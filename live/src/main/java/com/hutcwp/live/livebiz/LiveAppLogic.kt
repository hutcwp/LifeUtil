package com.hutcwp.live.livebiz

import com.hutcwp.live.livebiz.base.util.MLog
import com.hutcwp.live.livebiz.protocol.ChatMsgProtocolEvent
import com.hutcwp.tcp.TcpManager
import me.hutcwp.app.BaseAppLogic

/**
 *  author : kevin
 *  date : 2022/10/29 22:45
 *  description :
 */
class LiveAppLogic : BaseAppLogic() {

    override fun onCreate() {
        super.onCreate()

        MLog.info("logic", "LiveAppLogic onCreate")
        TcpManager.registerEvent(ChatMsgProtocolEvent())
    }
}