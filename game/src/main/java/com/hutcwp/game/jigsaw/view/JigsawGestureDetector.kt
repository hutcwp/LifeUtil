package com.hutcwp.game.jigsaw.view

import android.view.GestureDetector
import android.view.MotionEvent
import me.hutcwp.log.MLog
import kotlin.math.abs

/**
 *
 * Created by hutcwp on 2020/4/6 01:37
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class JigsawGestureDetector(private val mController: IController) : GestureDetector.OnGestureListener {

    override fun onShowPress(e: MotionEvent?) {
        //
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        val dx = e2.x - e1.x
        val dy = e2.y - e1.y
        val isVertical = abs(dy) > abs(dx)
        MLog.debug(TAG, "dx=$dx, dy=$dy, isVertical=$isVertical")
        if (isVertical) {
            if (dy > 0) {
                mController.toTop()
            } else {
                mController.toBottom()
            }
        } else {
            if (dx > 0) {
                mController.toLeft()
            } else {
                mController.toRight()
            }
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //
    }

    companion object {
        const val TAG = "JigsawGestureDetector"
    }
}