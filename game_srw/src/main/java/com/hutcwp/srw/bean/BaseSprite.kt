package com.hutcwp.srw.bean

import com.hutcwp.srw.BaseUI

/**
 *  author : kevin
 *  date : 2022/3/6 10:26 PM
 *  description : 所有的物体基础类
 */
open class BaseSprite(val view: BaseUI, var pos: Pos, val resId: Int) {

    init {
        view.setBackgroundResource(resId)
    }


}