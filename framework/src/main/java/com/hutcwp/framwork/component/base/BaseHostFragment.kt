package com.hutcwp.framwork.component.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.presenter.util.MLog


/**
 * Description:
 *
 * Created by n24314 on 2022/7/28. E-mail: caiwenpeng@corp.netease.com
 */
abstract class BaseHostFragment<Component, Binding : ViewDataBinding> : BaseViFragment<Binding>(), IHost<Component> {

    private val componentManager by lazy { ComponentManager<Component>() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(this, childFragmentManager, getComponentToIdMap())
    }

    /**
     * 初始化components
     */
    fun initComponents(
        host: IHost<Component>,
        fragmentManager: FragmentManager,
        componentToIdMap: Map<out BaseComponent<Component, out ViewDataBinding>, Int>
    ) {
        val transaction = fragmentManager.beginTransaction()
        componentToIdMap.forEach { entry ->
            val component = entry.key
            val fragment = entry.key as Fragment
            val id = entry.value
            val componentName = component.componentTag

            component.componentHost = host

            componentManager.addComponent(componentName, id, component as IComponent<Component>)

            MLog.info(
                "BaseHostFragment",
                "initComponents: id=${id}, fragment=$fragment , tag=${component.componentTag}"
            )
            transaction.replace(id, fragment, component.tag)
        }

        transaction.commitAllowingStateLoss()
    }

    /**
     * 定义所需要加载的组件和对应的布局id之间的关系
     */
    abstract fun getComponentToIdMap(): Map<out BaseComponent<Component, out ViewDataBinding>, Int>


    override fun <Component> getComponent(cls: Class<Component>): Component? {
        return componentManager.getComponent(cls)
    }

    override fun onDestroy() {
        super.onDestroy()
        componentManager.clearComponent()
    }

}