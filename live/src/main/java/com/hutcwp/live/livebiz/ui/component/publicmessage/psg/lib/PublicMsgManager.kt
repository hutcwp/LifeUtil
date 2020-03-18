package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

/**
 *
 * Created by hutcwp on 2020-03-13 18:10
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicMsgManager : IPublicChat<BaseChatMsg> {

    private var mAdapter: PublicChatAdapter? = null
    private var mPublicMsgView: PublicChatView? = null

    override fun addMsg(msg: BaseChatMsg) {
        mAdapter?.addItem(msg)
    }

    override fun addMsgs(msgs: List<BaseChatMsg>) {
        mAdapter?.addItemList(msgs)
    }

    override fun updateMsgs(msgs: List<BaseChatMsg>) {

    }

    fun setAdapter(adapter: PublicChatAdapter) {
        mAdapter = adapter
    }

    fun setPublicMsgView(publicChatView: PublicChatView) {
        mPublicMsgView = publicChatView
    }

    fun onRelease() {
        mAdapter = null
        mPublicMsgView = null
    }

}