package com.hutcwp.read.ui.home.tabs

import com.hutcwp.read.entitys.ReadCategory
import hut.cwp.core.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog


/**
 * Created by hutcwp on 2018/10/13 17:05
 *
 *
 */
open class TopPresenter : MvpPresenter<TopFragment>() {


    open fun getCategoryV2() {
        MLog.info(TAG, "getCategoryV2")
        GlobalScope.launch(Dispatchers.Main) {
            val readCategories = withContext(Dispatchers.IO) {
                getCategories()
            }
            view?.initTabLayout(readCategories)
        }
    }

    open suspend fun getCategories(): MutableList<ReadCategory> {
        return emptyList<ReadCategory>().toMutableList()
    }


    companion object {
        const val TAG = "ReadPresenter"
    }
}
