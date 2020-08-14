package club.hutcwp.lifeutil.ui.home.sub.picture

import club.hutcwp.lifeutil.core.PhotoCatagoryParseImpl
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        MLog.info(TAG, "getGank")
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val photoList = getDataAsyc()
                MLog.info(TAG, "getDataAsyc = $photoList")
                curPage++
                if (isRefresh) {
                    view?.setNewData(photoList)
                } else {
                    view?.addNewData(photoList)
                }
                view?.showSnack("加载完成")
                view?.setRefreshing(false)
            } catch (e: Exception) {
                view?.showSnack("加载失败")
                view?.setRefreshing(false)
                MLog.error(TAG, "catch error", e)
            } finally {
                MLog.info(TAG, "finally")
            }
        }
    }

    private suspend fun getDataAsyc() = withContext(Dispatchers.IO) {
        val response = ApiFactory.getGirlsController()?.getNewGankAsyc(curPage)
        val photoList = mutableListOf<Photo>()
        MLog.info(TAG, "get Data from conroutine $response")
        response?.data?.forEach {
            val photo = Photo()
            photo.date = it.createdAt
            photo.name = it.title
            photo.img = it.images.firstOrNull()
            photoList.add(photo)
        }
        MLog.info(TAG, "photoList=$photoList")
        photoList
    }


    fun getServer() {
        MLog.info(TAG, "getServer")
        dataFromServer?.subscribe {
            MLog.info(TAG, "data = $it")
        }
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
