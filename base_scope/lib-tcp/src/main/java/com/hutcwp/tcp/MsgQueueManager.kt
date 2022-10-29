package com.hutcwp.tcp

import android.util.Log
import com.hutcwp.tcp.protocol.TcpProtocol
import com.hutcwp.tcp.response.TcpRspBean
import java.util.concurrent.LinkedBlockingQueue

/**
 * 消息队列管理器
 */
object MsgQueueManager {


    private var sendMsgQueue: LinkedBlockingQueue<TcpProtocol>? = null



    fun onResponse(tcpRsp: TcpRspBean) {
        Log.i("hutcwp", "接收到协议：rsp=$tcpRsp")
    }

    fun registerSocket(sendMsgQueue: LinkedBlockingQueue<TcpProtocol>) {
        this.sendMsgQueue = sendMsgQueue
    }


    fun sendMsg(tcpProtocol: TcpProtocol) {
        sendMsgQueue?.add(tcpProtocol)
    }


    interface RspCallback {
        fun onResponse(msg: TcpProtocol) {

        }
    }

}
