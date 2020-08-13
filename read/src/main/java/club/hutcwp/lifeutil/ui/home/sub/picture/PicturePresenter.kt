package club.hutcwp.lifeutil.ui.home.sub.picture

import club.hutcwp.lifeutil.core.PhotoCatagoryParseImpl
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog
import java.util.*


/**
 * Created by hutcwp on 2018/10/13 20:45
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */

class PicturePresenter : MvpPresenter<IPicture>() {

    private var curPage = 1
    private var isRefresh = true
    private val compositeDisposable = CompositeDisposable()

    private val gankObservable: Observable<List<Photo>> = ApiFactory.getGirlsController()?.getNewGank(curPage)?.subscribeOn(Schedulers.io())!!.map { response ->
        MLog.info(TAG, "gankObservable: a page=$curPage")
        val photoList = mutableListOf<Photo>()
        response.data?.forEach {
            val photo = Photo()
            photo.date = it.createdAt
            photo.name = it.title
            photo.img = it.images.firstOrNull()
            photoList.add(photo)
        }
        photoList
    }

    private val dataFromServer: Observable<List<Photo>>
        get() {
            MLog.info(TAG, "dataFromServer")
            val url = arguments.getString("url")!! + curPage
            return Observable.just(url)
                    .subscribeOn(Schedulers.io())
                    .map { path ->
                        val photos = ArrayList<Photo>()
                        photos.addAll(PhotoCatagoryParseImpl().parseHtmlFromUrl(path))
                        photos
                    }
        }


    fun getGank() {
        getData(gankObservable)
    }

    private fun getData(observable: Observable<List<Photo>>) {
        MLog.info(TAG, "getData")
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Photo>> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(photos: List<Photo>) {
                        curPage++
                        if (isRefresh) {
                            view?.setNewData(photos)
                        } else {
                            view?.addNewData(photos)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view?.showSnack("加载失败")
                        view?.setRefreshing(false)
                    }

                    override fun onComplete() {
                        view?.showSnack("加载完成")
                        view?.setRefreshing(false)
                    }
                })
    }

    fun getServer() {
        MLog.info(TAG, "getServer")
        getData(dataFromServer)
    }

    fun resetPage(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        if (isRefresh) {
            curPage = 1
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        private const val TAG = "PicturePresenter"
    }
}
