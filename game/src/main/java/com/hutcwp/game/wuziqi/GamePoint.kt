package com.hutcwp.game.wuziqi

import androidx.annotation.ColorInt

/**
 * Created by hutcwp on 2020-03-21 14:36
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * type 白色球 ： 0  黑色球 ：1
 */
class GamePoint(val x: Float, val y: Float, val type: Int, @ColorInt val color: Int) {

    fun compare(x: Float, y: Float): Boolean {
        return this.x == x && this.y == y
    }

    fun isHaving(x: Float, y: Float, t: Int): Boolean {
        if (this.x == x && this.y == y) {
            if (type == t) return true
        }
        return false
    }

}