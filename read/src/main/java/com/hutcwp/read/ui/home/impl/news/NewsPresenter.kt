package com.hutcwp.read.ui.home.impl.news

import com.hutcwp.read.entitys.News
import com.hutcwp.read.http.ApiFactory
import hut.cwp.core.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog


/**
 * Created by hutcwp on 2018/10/13 22:33
 */
class NewsPresenter : MvpPresenter<INews>() {

    private var reqPage = 0

    companion object {
        const val TAG = "NewsPresenter"
    }

    fun getDataFromServer(isInit: Boolean) {
        val url = arguments.getString("url") ?: ""

        view?.setRefreshing(true)
        if (isInit) {
            reqPage = 0
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val list = withContext(Dispatchers.IO) {
                    val response = ApiFactory.getGirlsController().getReadCategory(url, reqPage)
                    MLog.info(TAG, "getReadCategory: response=$response")

                    reqPage = response.page + 1

                    val news = mutableListOf<News>()
                    response.data?.forEach {
                        val item = News()
                        item.from = it.author
                        item.icon = it.images.firstOrNull()
                        item.name = it.title
                        item.updateTime = it.publishedAt
                        item.url = it.url
                        news.add(item)
                    }
                    news
                }

                if (isInit) {
                    view?.setNewData(list)
                } else {
                    view?.addNewData(list)
                }
            } catch (e: Exception) {
                view?.showSnack("加载失败")
                view?.setRefreshing(false)
                MLog.error(TAG, "getReadCategory error, see error below:", e)
            } finally {
                view?.setRefreshing(false)
            }
        }
    }

}
