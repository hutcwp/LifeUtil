package com.hutcwp.live.livebiz.core

import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg

/**
 *
 * Created by hutcwp on 2020-03-19 15:17
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IPublicMessageCore {

    fun sendPublicMessage(msg: MyChatMsg)

    fun sendDanmuMessage(msg: MyChatMsg)
}