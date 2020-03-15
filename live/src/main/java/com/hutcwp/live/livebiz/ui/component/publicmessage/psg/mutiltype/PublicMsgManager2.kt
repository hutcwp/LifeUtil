package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.IPublicChat

/**
 *
 * Created by hutcwp on 2020-03-13 18:10
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicMsgManager2 : IPublicChat<BaseChatMsg> {

    private var mAdapter: PublicChatAdapter2? = null
    private var mPublicMsgView: PublicChatView2? = null

    override fun addMsg(msg: BaseChatMsg) {
        mAdapter?.addItem(msg)
    }

    override fun addMsgs(msgs: List<BaseChatMsg>) {
        mAdapter?.addItemList(msgs)
    }

    override fun updateMsgs(msgs: List<BaseChatMsg>) {

    }

    fun setAdapter(adapter: PublicChatAdapter2) {
        mAdapter = adapter
    }

    fun setPublicMsgView(publicChatView: PublicChatView2) {
        mPublicMsgView = publicChatView
    }

    fun onRelease() {
        mAdapter = null
        mPublicMsgView = null
    }

}