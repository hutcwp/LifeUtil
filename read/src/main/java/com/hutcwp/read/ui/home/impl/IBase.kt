package com.hutcwp.read.ui.home.impl

import hut.cwp.core.MvpView


/**
 * Created by hutcwp on 2018/10/14 18:37
 *
 *
 */
interface IBase<T> : MvpView {
    fun showSnack(msg: String)
    val data: List<T>
    fun setRefreshing(status: Boolean)
    fun setNewData(data: List<T>)
    fun addNewData(data: List<T>)
    fun addNewData(pos: Int, data: List<T>)
    fun hasMore(hasMore: Boolean)
}
