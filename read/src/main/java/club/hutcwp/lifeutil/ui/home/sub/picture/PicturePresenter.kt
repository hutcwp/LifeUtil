package club.hutcwp.lifeutil.ui.home.sub.picture

import club.hutcwp.lifeutil.core.DoubanPhotoParseImpl
import club.hutcwp.lifeutil.core.PhotoCatagoryParseImpl
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog
import okhttp3.*
import java.io.IOException
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


    private val gankObservable: Observable<List<Photo>>
        get() = ApiFactory.getGirlsController()?.getGank(curPage.toString() + "")?.subscribeOn(Schedulers.io())!!.map { response ->
            val photos = ArrayList<Photo>()
            photos.addAll(response.datas!!)
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
                        photos.addAll(PhotoCatagoryParseImpl().parse(path))
                        photos
                    }
        }

    private val dataFromServer2: Observable<List<Photo>>
        get() {
            val url = "https://meizi.leanapp.cn/category/All/page/1"
            val client = OkHttpClient()
            val request: Request = Request.Builder().get().url(url).build()
            val call: Call = client.newCall(request)
            //异步调用并设置回调函数
            return Observable.create {
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        MLog.error(TAG, "parseHtmlFromUrl error", e)
                        it.onError(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val content = response.body()?.string() ?: ""
                        MLog.info(TAG, "parseHtmlFromUrl onResponse, response=${content}")
                        it.onNext(DoubanPhotoParseImpl().parse(content))
                        it.onComplete()
                    }
                })
            }
        }

    fun gerStrge(): Observable<List<Photo>> {
        return dataFromServer2
    }

    fun serRefresh(isRefresh: Boolean) {
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
