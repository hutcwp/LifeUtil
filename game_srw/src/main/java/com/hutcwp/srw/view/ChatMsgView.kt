package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import kotlinx.android.synthetic.main.view_chat_msg.view.*

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

    fun setChatMsg(speakerImg: Int, msg: String) {
        tv_chat_msg?.text = msg
        if (speakerImg > 0) {
            iv_speaker?.setImageResource(speakerImg)
        }
    }
}