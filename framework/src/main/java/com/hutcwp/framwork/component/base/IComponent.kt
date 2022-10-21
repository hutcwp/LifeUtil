package com.hutcwp.framwork.component.base

/**
 * Description: 组件接口
 *
 * Created by n24314 on 2022/7/28. E-mail: caiwenpeng@corp.netease.com
 */
interface IComponent<Component> {

    fun getIHost(): IHost<Component>?

}