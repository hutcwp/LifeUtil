package com.hutcwp.srw.bean

/**
 *  author : kevin
 *  date : 2022/3/6 6:55 PM
 *  description :
 */
class RobotParams(val resId: Int, val pos: Pos) {


    class Builder {
        var resId: Int = -1
        var pos: Pos = Pos(0, 0)

        fun build() = RobotParams(resId, pos)
    }

}


class Pos(val x: Int, val y: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pos

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}