package com.hutcwp.srw.controller

import com.hutcwp.srw.bean.RobotSprite

/**
 *  author : kevin
 *  date : 2022/11/10 00:41
 *  description : 菜单容器接口
 */
interface IMenuContainer {

    fun showControllerMenuDialog(sprite: RobotSprite)

    fun dismissMenu()

}