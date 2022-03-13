package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R

/**
 *  author : kevin
 *  date : 2022/3/13 11:43 AM
 *  description :
 */
class ChatMsgView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    init {
        View.inflate(context, R.layout.view_chat_msg, this)
    }
}