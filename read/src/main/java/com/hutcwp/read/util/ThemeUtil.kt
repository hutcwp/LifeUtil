package com.hutcwp.read.util


import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import android.util.TypedValue

import com.hutcwp.read.R

/**
 * 多主题切换工具
 * Created by liyu on 2016/9/28.
 */

object ThemeUtil {

    fun getThemeColor(context: Context, attrRes: Int): Int {
        val typedArray = context.obtainStyledAttributes(intArrayOf(attrRes))
        val color = typedArray.getColor(0, 0xffffff)
        typedArray.recycle()
        return color
    }

    fun getCurrentColorPrimary(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.resourceId
    }

    fun getCurrentColorPrimaryDark(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        return typedValue.resourceId
    }

    fun getCurrentColorAccent(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.resourceId
    }

    fun setTintDrawable(drawable: Drawable, context: Context): Drawable {
        val drawable1 = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(drawable1, context.resources.getColorStateList(getCurrentColorPrimary(context)))
        return drawable1
    }

    fun setTintDrawable(@DrawableRes drawable: Int, context: Context, @ColorRes color: Int): Drawable {
        val drawable1 = DrawableCompat.wrap(context.resources.getDrawable(drawable))
        DrawableCompat.setTintList(drawable1, context.resources.getColorStateList(color))
        return drawable1
    }
}
