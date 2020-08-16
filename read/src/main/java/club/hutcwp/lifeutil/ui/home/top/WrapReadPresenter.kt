package club.hutcwp.lifeutil.ui.home.top

import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.http.ApiFactory

/**
 * @ProjectName: LifeUtil$
 * @Package: club.hutcwp.lifeutil.ui.home.top$
 * @ClassName: ReadPresenter2$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 6:01 PM$
 */
class WrapReadPresenter : TopPresenter() {

    override suspend fun getCategories(): MutableList<ReadCategory> {
        val readList = ApiFactory.getGirlsController().getArticleList()
        val categoryList = mutableListOf<ReadCategory>()
        readList.data?.forEach {
            val readCategory = ReadCategory(it.title, it.type)
            categoryList.add(readCategory)
        }
        return categoryList
    }
}