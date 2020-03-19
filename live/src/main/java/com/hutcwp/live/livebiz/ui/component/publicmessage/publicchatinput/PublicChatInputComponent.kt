package com.hutcwp.live.livebiz.ui.component.publicmessage.publicchatinput

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.hutcwp.live.livebiz.base.util.MLog
import com.hutcwp.live.livebiz.core.PublicMessageManager
import com.hutcwp.live.livebiz.ui.component.Component
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.TestUtils
import com.hutcwp.livebiz.R
import hut.cwp.mvp.BindPresenter

/**
 *
 * Created by hutcwp on 2020-03-19 10:57
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
@BindPresenter(presenter = PublicChatPresenter::class)
class PublicChatInputComponent : Component<PublicChatPresenter, IPublicChatInput>(), IPublicChatInput {

    private var btnSend: Button? = null
    private var etInput: EmotionChatEditText? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.component_chat_input, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View?) {
        btnSend = view?.findViewById(R.id.btn_send)
        etInput = view?.findViewById(R.id.input_box)
        initListener()
    }

    private fun initListener() {
        btnSend?.setOnClickListener {
            MLog.debug(TAG, "btnSend click")
            val content = etInput?.text?.trim().toString()
            if (content.isNotEmpty()) {
                val msg = TestUtils.getNormalMsg()
                msg.content = content
                etInput?.setText("")
                hideKeyboard(it)
                PublicMessageManager.sendPublicMessage(msg)
            }
        }
        etInput?.setOnSendEnableListener { enable ->
            btnSend?.isEnabled = enable
            val resId = if (enable) R.drawable.btn_yellow_selector_corner_90 else R.drawable.bg_chat_input
            val color: Int = if (enable) R.color.txt_color_unfollow else R.color.common_color_9
            btnSend?.setBackgroundResource(resId)
            btnSend?.setTextColor(resources.getColor(color))
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val TAG = "PublicChatInputComponent"
    }
}