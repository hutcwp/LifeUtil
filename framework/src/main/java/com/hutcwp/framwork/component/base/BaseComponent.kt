package com.hutcwp.framwork.component.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Description:
 *
 * Created by n24314 on 2022/7/28. E-mail: caiwenpeng@corp.netease.com
 */
abstract class BaseComponent<Component, Binding : ViewDataBinding> : BaseViFragment<Binding>(),
    IComponent<Component> {


    /**
     * 所在的宿主
     */
    var componentHost: IHost<Component>? = null

    var componentTag: String = this@BaseComponent.javaClass.name //标识组件，后续可以这个来找组件
        protected set

    override fun getIHost(): IHost<Component>? {
        return componentHost
    }

    fun <Component> getComponent(cls: Class<Component>): Component? {
        return componentHost?.getComponent(cls)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false, null)
        return binding.root
    }

}