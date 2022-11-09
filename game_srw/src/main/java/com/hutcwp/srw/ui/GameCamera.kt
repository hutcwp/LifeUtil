package com.hutcwp.srw.ui

import com.hutcwp.srw.ui.view.MapView
import me.hutcwp.BasicConfig
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils

/**
 *  author : kevin
 *  date : 2022/11/9 21:36
 *  description :
 *  游戏摄像机，用来控制镜头跟随光标移动
 */
class GameCamera(private val mapView: MapView) {

    private fun adjust(direction: Direction, dx: Int) {
        var x = 0
        var y = 0
        when (direction) {
            Direction.TOP -> {
                y = Math.abs(dx)
            }
            Direction.BOTTOM -> {
                y = -Math.abs(dx)
            }
            Direction.LEFT -> {
                x = Math.abs(dx)
            }
            Direction.RIGHT -> {
                x = -Math.abs(dx)
            }
            else -> {
                return
            }
        }

        tryMove(x, y)
    }

    private fun tryMove(x: Int, y: Int) {
        if (canMove(x, y)) {
            move(x, y)
        } else {
            MLog.info("GameCamera", "无法移动")
        }
    }

    private fun canMove(x: Int, y: Int): Boolean {
        val location = IntArray(2)
        mapView.getLocationOnScreen(location) //获取在整个屏幕内的绝对坐标
        val rawX = location[0]
        val rawY = location[1]
        val top = rawY
        val bottom = rawY + mapView.height
        val left = rawX
        val right = rawX + mapView.width

        val screenH = ResolutionUtils.getScreenHeight(BasicConfig.getApplicationContext())
        val screenW = ResolutionUtils.getScreenWidth(BasicConfig.getApplicationContext())

        MLog.debug("GameCamera", "rawX=$rawX, rawY=$rawY")
        MLog.debug("GameCamera", "top=$top, bottom=$bottom, left=$left, right=$right")
        //x,y大于0，往右和下
//        if (top + y <= cameraTop && bottom + y >= cameraBottom
//            && left + x <= cameraLeft && right + x >= cameraRight
//        ) {
        return true
//        }

//        return false
    }

    private fun move(x: Int, y: Int) {
        val location = IntArray(2)
        mapView.getLocationOnScreen(location) //获取在整个屏幕内的绝对坐标

        val rawX = location[0]
        val rawY = location[1]

        mapView.offsetLeftAndRight(x)
        mapView.offsetTopAndBottom(y)

        MLog.debug("GameCamera", "rawX=$rawX, rawY=$rawY, 移动：x=$x, y=$y")
    }

    /**
     * 方向
     */
    enum class Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    fun up() {
        adjust(Direction.TOP, mapView.mapUnit)
    }

    fun down() {
        adjust(Direction.BOTTOM, mapView.mapUnit)
    }

    fun left() {
        adjust(Direction.LEFT, mapView.mapUnit)
    }

    fun right() {
        adjust(Direction.RIGHT, mapView.mapUnit)
    }

}