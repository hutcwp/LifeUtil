package club.hutcwp.lifeutil.ui.home.top

import club.hutcwp.lifeutil.entitys.ReadCategory

/**
 * @ProjectName: LifeUtil$
 * @Package: club.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapPhotoPresenter$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 7:15 PM$
 */
internal class WrapPhotoPresenter : TopPresenter() {

    override suspend fun getCategories(): MutableList<ReadCategory> {
        val categoryList = mutableListOf<ReadCategory>()
        categoryList.add(ReadCategory("靓女专题", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/"))
        return categoryList
    }
}