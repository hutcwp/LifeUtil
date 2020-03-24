package com.hutcwp.game.wuziqi

import androidx.annotation.ColorInt

/**
 * Created by hutcwp on 2020-03-21 14:36
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * type 白色球 ： 0  黑色球 ：1
 */
class GamePoint(val x: Int, val y: Int, val type: Int, @ColorInt val color: Int) {

    fun compare(x: Int, y: Int): Boolean {
        return this.x == x && this.y == y
    }

    fun isHaving(x: Int, y: Int, t: Int): Boolean {
        if (this.x == x && this.y == y) {
            if (type == t) return true
        }
        return false
    }

}