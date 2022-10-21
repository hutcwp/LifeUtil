package com.hutcwp.framwork.component.base

/**
 * Description: host接口
 *
 * Created by n24314 on 2022/7/28. E-mail: caiwenpeng@corp.netease.com
 */
interface IHost<Component> {

    /**
     * 获取组件
     */
    fun <Component> getComponent(cls: Class<Component>): Component?


}