package com.hutcwp.srw.ui

import android.view.View
import com.hutcwp.srw.ui.view.MapView
import com.hutcwp.srw.util.getRawPos
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/11/9 21:36
 *  description :
 *  游戏摄像机，用来控制镜头跟随光标移动
 */
class GameCamera(
    private val mapView: MapView,
    private val cameraView: View
) {


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

        tryMove(-x, -y)
    }

    fun tryMove(x: Int, y: Int) {
        if (canMove(x, y)) {
            move(x, y)
        } else {
            MLog.info("GameCamera", "无法移动")
        }
    }

    private fun canMove(x: Int, y: Int): Boolean {
        val mapRawPos = mapView.getRawPos()
        val cameraPos = cameraView.getRawPos()

        MLog.debug(
            "GameCamera", "top=${mapRawPos.top}, bottom=${mapRawPos.bottom}, " +
                    "left=${mapRawPos.left}, right=${mapRawPos.right}"
        )

        //x,y大于0，往右和下
        if (mapRawPos.top + y + mapView.translationY <= cameraPos.top
            && mapRawPos.bottom + y + mapView.translationY >= cameraPos.bottom
            && mapRawPos.left + x + mapView.translationX <= cameraPos.left
            && mapRawPos.right + x + mapView.translationX >= cameraPos.right
        ) {
            return true
        }

        return false
    }

    private fun move(x: Int, y: Int) {
        val location = IntArray(2)
        mapView.getLocationOnScreen(location) //获取在整个屏幕内的绝对坐标

        val rawX = location[0]
        val rawY = location[1]

        MLog.debug("GameCamera", "before move left=${mapView.left},right=${mapView.right}")
//        mapView.offsetLeftAndRight(x)
//        mapView.offsetTopAndBottom(y)

        mapView.tryTranslate(x, y)

        MLog.debug("GameCamera", "after move left=${mapView.left},right=${mapView.right}")


        MLog.debug("GameCamera", "move: rawX=$rawX, rawY=$rawY, 移动：x=$x, y=$y")
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


    /**
     * 屏幕位置
     */
    data class RawPos(
        val left: Int,
        val top: Int,
        val right: Int,
        val bottom: Int
    )

}