package com.hutcwp.srw.ui.view

import com.hutcwp.srw.bean.BaseSprite

/**
 *  author : kevin
 *  date : 2022/3/9 9:34 PM
 *  description :
 */
interface IControllerMenu {

    fun move()

    fun status()

    fun finish()

    fun skill()

    fun select(sprite: BaseSprite)

    fun attack()

}