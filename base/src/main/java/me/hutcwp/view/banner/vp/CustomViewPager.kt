package me.hutcwp.view.banner.vp

import android.content.Context

import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


/**
 * Created by hutcwp on 2019-10-22 16:50
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */

class CustomViewPager : ViewPager {

    //是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    // false 禁止ViewPager左右滑动。
    private var scrollable = false


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }

    fun toLeftPage(): Boolean {
        if (currentItem > 0) {
            setCurrentItem(currentItem - 1, true)
            return true
        }
        return false
    }

    fun toRightPage(): Boolean {
        if (adapter != null && currentItem < adapter!!.count - 1) {
            setCurrentItem(currentItem + 1, true)
            return true
        }
        return false
    }

//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        return scrollable
//    }
//
//    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        return scrollable
//    }
}
