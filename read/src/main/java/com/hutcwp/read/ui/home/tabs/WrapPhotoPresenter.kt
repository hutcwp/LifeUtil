package com.hutcwp.read.ui.home.tabs

import com.hutcwp.read.entitys.ReadCategory

/**
 * @ProjectName: LifeUtil$
 * @Package: com.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapPhotoPresenter$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/16$ 7:15 PM$
 */
internal class WrapPhotoPresenter : TopPresenter() {

    override suspend fun getCategories(): MutableList<ReadCategory> {
        val categoryList = mutableListOf<ReadCategory>()
        categoryList.add(ReadCategory("靓女专题", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/"))
        return categoryList
    }
}