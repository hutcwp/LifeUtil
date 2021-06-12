package com.hutcwp.read.ui.home.impl.picture

import com.hutcwp.read.entitys.Photo
import com.hutcwp.read.http.ApiFactory
import com.hutcwp.read.ui.home.impl.news.NewsPresenter
import hut.cwp.core.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog

/**
 * Created by hutcwp on 2018/10/13 20:45
 */
class PicturePresenter : MvpPresenter<IPicture>() {

    private var curPage = 1
    private var total = -1
    private val compositeDisposable = CompositeDisposable()

    fun initData() {
        getGank(true)
    }

    fun loadMore() {
        getGank(false)
    }

    private fun getGank(isInit: Boolean) {
        if (isInit) {
            curPage = 1
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val photoList = getDataAsyc()
                if (isInit) {
                    view?.setNewData(photoList)
                } else {
                    view?.addNewData(photoList)
                }

                val hasMore = view?.data?.size ?: 0 < total
                view?.hasMore(hasMore)
            } catch (e: Exception) {
                view?.showSnack("加载失败")
                view?.setRefreshing(false)
                MLog.error(NewsPresenter.TAG, "getGank error, see error below:", e)
            } finally {
//                view?.showSnack("加载完成")
                view?.setRefreshing(false)
            }
        }
    }

    private suspend fun getDataAsyc() = withContext(Dispatchers.IO) {
        val response = ApiFactory.getGirlsController().getNewGankAsyc(curPage)

        MLog.info(TAG, "getGank success response: $response")
        if (response.status != 100) {
            throw java.lang.Exception("接口请求错误status!=100.")
        }

        curPage = response.page + 1
        total = response.total_counts
        val photoList = response.data.map {
            val photo = Photo()
            photo.date = it.createdAt
            photo.name = it.title
            photo.img = it.images.firstOrNull()
            photo
        }

        photoList
    }


    public override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    companion object {
        private const val TAG = "PicturePresenter"
    }
}
