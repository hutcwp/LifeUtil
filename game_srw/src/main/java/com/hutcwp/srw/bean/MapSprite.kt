package com.hutcwp.srw.bean

import android.content.Context
import com.hutcwp.srw.ui.BaseUI

/**
 *  author : kevin
 *  date : 2022/3/6 10:27 PM
 *  description :
 */
class MapSprite(val context: Context, resId: Int, pos: Pos) : BaseSprite(BaseUI(context), pos, resId) {

    fun showEnable() {
        view.alpha = 1f
    }

    fun showUnable() {
        view.alpha = 0.3f
    }

    fun isEnable():Boolean{
        return view.alpha == 1f
    }
}