package com.hutcwp.srw.bean

/**
 *  author : kevin
 *  date : 2022/3/6 6:55 PM
 *  description :
 */
class RobotParams(val resId: Int, val pos: Pos) {


    class Builder {
        var resId: Int = -1
        var pos: Pos = Pos(0F, 0F)

        fun build() = RobotParams(resId, pos)
    }

}


class Pos(val x: Float, val y: Float) {

    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    constructor(pos: Pos) : this(pos.x, pos.y)

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
        return result.toInt()
    }

    override fun toString(): String {
        return "Pos(x=$x, y=$y)"
    }
}