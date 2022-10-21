package com.hutcwp.framwork.component.base

/**
 * Description: 组件管理类
 *
 * Created by n24314 on 2022/7/28. E-mail: caiwenpeng@corp.netease.com
 */
class ComponentManager<Component> {

    private var componentMap: MutableMap<String, Pair<IComponent<Component>, Int>> = mutableMapOf()


    fun addComponent(name: String, id: Int, component: IComponent<Component>) {
        componentMap[name] = Pair(component, id)
    }

    fun <Component> getComponent(cls: Class<Component>): Component? {
        return this.getComponent<Component>(cls.name)
    }

    fun <Component> getComponent(name: String): Component? {
        val component = componentMap[name]
        component ?: return null

        return component.first as? Component
    }


//    private fun <Component, Binding : ViewDataBinding> filterComponent(
//        componentToIdMap: Map<out BaseComponent<Component, Binding>, Int>
//    ): Map<out BaseComponent<Component, Binding>, Int> {
//        /**
//         * TODO 这里定义过滤逻辑，判断重复添加等等逻辑
//         */
//        return componentToIdMap
//    }

    fun clearComponent() {
        componentMap.clear()
    }

}