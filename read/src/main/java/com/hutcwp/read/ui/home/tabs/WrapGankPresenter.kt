package com.hutcwp.read.ui.home.tabs

import com.hutcwp.read.entitys.ReadCategory
import com.hutcwp.read.http.ApiFactory

/**
 * @ClassName: BaseGankPresenter$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/16$ 5:54 PM$
 */
class WrapGankPresenter : TopPresenter(){

    override suspend fun getCategories(): MutableList<ReadCategory> {
        val readList = ApiFactory.getGirlsController().getReadList()
        val categoryList = mutableListOf<ReadCategory>()
        readList.data?.forEach {
            val readCategory = ReadCategory(it.title, it.type)
            categoryList.add(readCategory)
        }
        return categoryList
    }
}