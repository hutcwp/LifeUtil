package com.hutcwp.live.livebiz.protocol

import com.hutcwp.event.AbstractTcpEvent

/**
 *  author : kevin
 *  date : 2022/10/29 22:14
 *  description :
 */
class ChatMsgProtocolEvent : AbstractTcpEvent() {

    override val sid = 2
    override val cid = 1

}