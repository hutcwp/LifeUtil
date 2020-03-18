package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

import androidx.recyclerview.widget.LinearLayoutManager

/**
 *
 * Created by hutcwp on 2020-03-13 18:10
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicChatPresenter : IPublicChat<BaseChatMsg>, ISimpleChat<BaseChatMsg> {

    private var mAdapter: PublicChatAdapter? = null
    private var mPublicMsgView: PublicChatView? = null
    private var iBufferChat: IBufferChat<BaseChatMsg>? = null

    init {
        iBufferChat = BufferChat(this, 300)
        iBufferChat?.play()
    }

    fun setAdapter(adapter: PublicChatAdapter) {
        mAdapter = adapter
    }

    fun setPublicMsgView(publicChatView: PublicChatView) {
        mPublicMsgView = publicChatView
    }

    private fun isAtBottom(): Boolean {
        if (mPublicMsgView?.layoutManager is LinearLayoutManager) {
            val pos = (mPublicMsgView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            val itemCount = mAdapter?.itemCount ?: 0
            return pos == itemCount - 1
        }
        return false
    }

    /**
     * IPublicChat接口实现
     */
    override fun addMessage(msg: BaseChatMsg) {
        iBufferChat?.addChat(msg)
    }

    override fun addChatMessages(msgList: List<BaseChatMsg>) {
        iBufferChat?.addChat(msgList)
    }

    override fun updatePublicMessages(msgList: List<BaseChatMsg>) {
        iBufferChat?.updateChat(msgList)
    }

    /**
     * ISimpleChat接口实现
     */
    override fun sendMultiMsg(msgList: List<BaseChatMsg>) {
        mAdapter?.addItemList(msgList)
    }

    override fun sendSingleMsg(msg: BaseChatMsg) {
        mAdapter?.addItem(msg)
    }

    override fun updateChatView(msgList: List<BaseChatMsg>) {
        mAdapter?.addItemList(msgList)
    }

    override fun updateChatView(msg: BaseChatMsg) {
        val isBottom = isAtBottom()
        if (isBottom) {
            mAdapter?.addItem(msg)
            mPublicMsgView?.updateViewPosition()
        } else {
            mAdapter?.addItem(msg)
        }
    }

    override fun release() {
        mAdapter = null
        mPublicMsgView = null
        iBufferChat?.release()
    }

}