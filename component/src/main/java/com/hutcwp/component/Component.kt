package com.hutcwp.component

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *  author : kevin
 *  date : 2021/12/12 12:12 AM
 *  description :
 */
open class Component constructor() : ILifecycle {

    private var mContentLayoutId: Int = 0

    var rootView: View? = null

    constructor(contentId: Int) : this() {
        this.mContentLayoutId = contentId
    }

    fun getInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }


    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return if (mContentLayoutId != 0) {
            rootView = inflater.inflate(mContentLayoutId, container, false)
            rootView
        } else null
    }

    override fun onCreate() {

    }

    override fun onViewCreated() {
    }

    override fun onDestroy() {
    }


}