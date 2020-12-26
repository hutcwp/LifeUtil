package club.hutcwp.lifeutil.ui.home.sub.picture

import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog

/**
 * Created by hutcwp on 2018/10/13 20:45
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class PicturePresenter : MvpPresenter<IPicture>() {

    private var curPage = 1
    private var isRefresh = true
    private var total = -1
    private val compositeDisposable = CompositeDisposable()


    fun getGank(isInit: Boolean) {
        MLog.info(TAG, "getGank")
        if (isInit) {
            curPage = 1
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val photoList = getDataAsyc()
                MLog.info(TAG, "getDataAsyc = $photoList")
                curPage++

                view?.showSnack("加载完成")
                view?.setRefreshing(false)

                (view as PictureFragment).let {
                    val hasMore = it.adapter!!.data.size < total
                    it.updateHasMore(hasMore)
                }
                if (isInit) {
                    view?.setNewData(photoList)
                } else {
                    view?.addNewData(photoList)
                }
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
        val response = ApiFactory.getGirlsController().getNewGankAsyc(curPage)
        val photoList = mutableListOf<Photo>()
        MLog.info(TAG, "get Data from conroutine $response")
        total = response.total_counts
        response.data?.forEach {
            val photo = Photo()
            photo.date = it.createdAt
            photo.name = it.title
            photo.img = it.images.firstOrNull()
            photoList.add(photo)
        }
        MLog.info(TAG, "photoList=$photoList")
        photoList
    }


    fun initData() {
        getGank(true)
    }

    fun loadMore() {
        getGank(false)
    }

    public override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    companion object {
        private const val TAG = "PicturePresenter"
    }
}
