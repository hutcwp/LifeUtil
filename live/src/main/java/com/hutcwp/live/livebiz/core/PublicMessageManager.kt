package com.hutcwp.live.livebiz.core

import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by hutcwp on 2020-03-19 15:14
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
object PublicMessageManager : IPublicMessageCore {


    override fun sendPublicMessage(msg: MyChatMsg) {
        EventBus.getDefault().post(msg)
    }

    override fun sendDanmuMessage(msg: MyChatMsg) {

    }
}