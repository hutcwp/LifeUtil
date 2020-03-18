package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Created by hutcwp on 2020-03-13 18:08
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicChatView : RecyclerView, IPublicChat<BaseChatMsg> {

    private var mPublicChatAdapter: PublicChatAdapter? = null
    private var mPublicChatPresenter: PublicChatPresenter? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        mPublicChatPresenter = PublicChatPresenter()
        mPublicChatPresenter?.setPublicMsgView(this)
    }

    fun setChatAdapter(adapter: PublicChatAdapter): PublicChatView {
        mPublicChatAdapter = adapter
        mPublicChatPresenter?.setAdapter(adapter)
        setAdapter(mPublicChatAdapter)
        return this
    }

    override fun addMessage(msg: BaseChatMsg) {
        mPublicChatPresenter?.addMessage(msg)
    }

    override fun addChatMessages(msgList: List<BaseChatMsg>) {
        mPublicChatPresenter?.addChatMessages(msgList)
    }

    override fun updatePublicMessages(msgList: List<BaseChatMsg>) {
        mPublicChatPresenter?.updatePublicMessages(msgList)
    }

    fun updateViewPosition() {
        mPublicChatAdapter?.let {
            scrollToPosition(it.itemCount - 1)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onRelease()
    }

    private fun onRelease() {
        mPublicChatPresenter?.release()
        mPublicChatPresenter = null
        mPublicChatAdapter = null
    }
}