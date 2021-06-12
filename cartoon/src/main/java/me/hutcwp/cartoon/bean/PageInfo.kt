package me.hutcwp.cartoon.bean

import androidx.annotation.DrawableRes

/**
 * Created by hutcwp on 2019-06-02 16:34
 *
 *
 */
data class PageInfo(
        @DrawableRes
        val drawableRes: Int,
        val title: String
)
