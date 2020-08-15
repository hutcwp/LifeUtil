package club.hutcwp.lifeutil.ui.home.sub.artical

import club.hutcwp.lifeutil.entitys.News
import hut.cwp.mvp.MvpView

/**
 * Created by hutcwp on 2018/10/13 22:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
interface IArticle : MvpView {
    val data: List<News>
    fun setRefreshing(status: Boolean)
    fun setNewData(data: List<News>)
    fun addNewData(pos: Int, data: List<News>)
}
