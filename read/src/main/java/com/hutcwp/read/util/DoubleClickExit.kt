package com.hutcwp.read.util

/**
 * Created by hutcwp on 2017/4/15.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

object DoubleClickExit {

    var mLastClick = 0L
    private val THRESHOLD = 2000

    fun check(): Boolean {
        val now = System.currentTimeMillis()
        val b = now - mLastClick < THRESHOLD
        mLastClick = now
        return b
    }
}
