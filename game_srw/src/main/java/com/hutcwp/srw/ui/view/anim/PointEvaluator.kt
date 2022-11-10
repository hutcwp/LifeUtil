package com.hutcwp.srw.ui.view.anim

import android.animation.TypeEvaluator
import com.hutcwp.srw.bean.Pos

/**
 *  author : kevin
 *  date : 2022/11/10 14:36
 *  description :
 */
class PointEvaluator : TypeEvaluator<Pos> {

    override fun evaluate(fraction: Float, startPoint: Pos, endPoint: Pos): Pos {

        val x = startPoint.x + fraction * (endPoint.x - startPoint.y)
        val y = startPoint.y + fraction * (endPoint.y - startPoint.y)

        return Pos(x, y)
    }
}