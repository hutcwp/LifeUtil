package com.hutcwp.tcp

import android.util.Log
import java.util.concurrent.LinkedBlockingQueue

/**
 * 消息队列管理器
 */
object MsgQueueManager {


    private var sendMsgQueue: LinkedBlockingQueue<MsgBean>? = null



    fun onResponse(tcpRsp: TcpRsp) {
        Log.i("hutcwp", "接收到协议：rsp=$tcpRsp")
    }

    fun registerSocket(sendMsgQueue: LinkedBlockingQueue<MsgBean>) {
        this.sendMsgQueue = sendMsgQueue
    }


    fun sendMsg(msgBean: MsgBean) {
        sendMsgQueue?.add(msgBean)
    }


    interface RspCallback {
        fun onResponse(msg: MsgBean) {

        }
    }

}
