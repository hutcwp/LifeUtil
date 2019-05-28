package club.hutcwp.lifeutil.ui.home.sub.picture

import android.util.Log

import java.util.ArrayList

import club.hutcwp.lifeutil.core.PhotoCatagoryParseImpl
import club.hutcwp.lifeutil.entitys.GankGirlPhoto
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.http.ApiFactory
import club.hutcwp.lifeutil.http.BaseGankResponse
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.Observer

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


/**
 * Created by hutcwp on 2018/10/13 20:45
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */

class PicturePresenter : MvpPresenter<IPicture>() {

    private var curPage = 1
    var isRefresh = true
        private set

    private val compositeDisposable = CompositeDisposable()


    private val gankObservable: Observable<List<Photo>>
        get() = ApiFactory.getGirlsController()?.getGank(curPage.toString() + "")?.subscribeOn(Schedulers.io())!!.map { response ->
            val photos = ArrayList<Photo>()
            photos.addAll(response.datas)
            photos
        }

    /**
     * 从服务器上获取数据
     */
    private val dataFromServer: Observable<List<Photo>>
        get() {
            val url = arguments.getString("url")!! + curPage
            return Observable.just(url)
                    .subscribeOn(Schedulers.io())
                    .map { path ->
                        val photos = ArrayList<Photo>()
                        photos.addAll(PhotoCatagoryParseImpl().parseHtmlFromUrl(path))
                        photos
                    }
        }

    /**
     * 获取数据
     */
    fun getGank() {
        getData(gankObservable)
    }


    private fun getData(observable: Observable<List<Photo>>) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Photo>> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(photos: List<Photo>) {
                        curPage++
                        if (isRefresh) {
                            view!!.setNewData(photos)
                        } else {
                            view!!.addNewData(photos)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view!!.showSnack("加载失败")
                        view!!.setRefreshing(false)
                    }

                    override fun onComplete() {
                        view!!.showSnack("加载完成")
                        view!!.setRefreshing(false)
                    }
                })

    }


    fun getServer() {
        getData(dataFromServer)
    }

    fun serRefresh(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        if (isRefresh) {
            curPage = 1
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    companion object {

        private val TAG = "PicturePresenter"
    }
}
