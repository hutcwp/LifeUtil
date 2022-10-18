package com.hutcwp.component

import android.content.Context
import android.view.ViewGroup

/**
 *  author : kevin
 *  date : 2021/12/12 1:33 AM
 *  description :
 */
class ComponentManager(val context: Context) {


    private val addedComponentList: MutableList<Component> = mutableListOf()

    fun replace(resId: Int, container: ViewGroup, component: Component) {
        val componentView = component.onCreateView(component.getInflater(context), container)
        container.findViewById<ViewGroup>(resId).addView(componentView)
        component.onViewCreated()
    }


    fun add(resId: Int, component: Component) {

    }

    fun commit() {

    }
}