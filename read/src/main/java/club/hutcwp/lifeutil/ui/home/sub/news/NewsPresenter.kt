package club.hutcwp.lifeutil.ui.home.sub.news

import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog


/**
 * Created by hutcwp on 2018/10/13 22:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class NewsPresenter : MvpPresenter<INews>() {

    private var disposable: Disposable? = null
    private var curPage = 0
    private val TAG = "NewsPresenter"

    fun getDataFromServerV2() {
        val type = arguments.getString("url") ?: ""
        view?.setRefreshing(true)
        MLog.info(TAG, "getDataFromServerV2: type=$type")
        GlobalScope.launch(Dispatchers.Main) {
            val list = withContext(Dispatchers.IO) {
                val newReadInfo = ApiFactory.getGirlsController()?.getReadCategory(type, 1)
                MLog.info(TAG, "newReadInfo=$newReadInfo")
                val news = mutableListOf<News>()
                newReadInfo?.data?.forEach {
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
            curPage++
            view?.let {
                it.setRefreshing(false)
                if (it.data.isEmpty()) {
                    it.setNewData(list)
                } else {
                    it.addNewData(it.data.size, list)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed)
            disposable!!.dispose()
    }
}
