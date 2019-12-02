package me.hutcwp.view.banner.vp

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 *
 * Created by hutcwp on 2019-10-22 16:16
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/

class LoopTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, p1: Float) {
        Log.i("test", "p1=$p1")
        var position = p1
        /**
         * 过滤那些 <-1 或 >1 的值，使它区于【-1，1】之间
         */
        if (position < -1) {
            position = -1f
        } else if (position > 1) {
            position = 1f
        }
        /**
         * 判断是前一页 1 + position ，右滑 pos -> -1 变 0
         * 判断是后一页 1 - position ，左滑 pos -> 1 变 0
         */
        val tempScale = if (position < 0) 1 + position else 1 - position // [0,1]
        val scaleValue = MIN_SCALE + tempScale * 0.1f // [0,1]
        view.scaleX = scaleValue
        view.scaleY = scaleValue
    }

    companion object {
        const val MIN_SCALE = 0.9f
    }

}
