package club.hutcwp.lifeutil.ui.home.top

import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.http.ApiFactory
import hut.cwp.mvp.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog


/**
 * Created by hutcwp on 2018/10/13 17:05
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class ReadPresenter : MvpPresenter<ReadFragment>() {

    private val TAG = "ReadPresenter"

    fun getCategoryV2() {
        MLog.info(TAG, "getCategoryV2")
        GlobalScope.launch(Dispatchers.Main) {
            val readCategories = withContext(Dispatchers.IO) {
                val readList = ApiFactory.getGirlsController()?.getReadList()
                val categoryList = mutableListOf<ReadCategory>()
                readList?.data?.forEach {
                    val readCategory = ReadCategory()
                    readCategory.name = it.title
                    readCategory.url = it.type
                    categoryList.add(readCategory)
                }
                categoryList
            }
            view?.initTabLayout(readCategories)
        }
    }
}
