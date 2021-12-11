package com.hutcwp.live.chat.intepreter

import android.text.Spannable

/**
 *  author : kevin
 *  date : 2021/11/6 1:33 AM
 *  description :
 */
interface IParseSpan<D> {

    fun parse(data: D): Spannable

}