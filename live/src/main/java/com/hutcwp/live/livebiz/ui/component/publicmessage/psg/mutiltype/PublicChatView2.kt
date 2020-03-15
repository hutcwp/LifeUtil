package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.IPublicChat

/**
 *
 * Created by hutcwp on 2020-03-13 18:08
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicChatView2 : RecyclerView, IPublicChat<BaseChatMsg> {

    private var mAdapter: PublicChatAdapter2? = null
    private var mManager: PublicMsgManager2? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        mManager = PublicMsgManager2()
        mManager?.setPublicMsgView(this)
    }

    fun setChatAdapter(adapter: PublicChatAdapter2): PublicChatView2 {
        mAdapter = adapter
        mManager?.setAdapter(adapter)
        setAdapter(mAdapter)
        return this
    }

    override fun addMsg(msg: BaseChatMsg) {
        mManager?.addMsg(msg)
        updateViewPosition()
    }

    override fun addMsgs(msgs: List<BaseChatMsg>) {
        mManager?.addMsgs(msgs)
        updateViewPosition()
    }

    override fun updateMsgs(msgs: List<BaseChatMsg>) {
        mManager?.updateMsgs(msgs)
        updateViewPosition()
    }

    fun setManager(manager: PublicMsgManager2) {
        mManager = manager
        mManager?.setPublicMsgView(this)
        mAdapter?.let {
            mManager?.setAdapter(it)
        }
    }

    private fun updateViewPosition() {
        if (!canScrollVertically(1)) {
            mAdapter?.let {
                scrollToPosition(it.itemCount - 1)
            }
        }
    }

}