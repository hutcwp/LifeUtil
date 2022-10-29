package com.hutcwp.tcp

import com.hutcwp.tcp.protocol.TcpProtocol
import java.util.concurrent.PriorityBlockingQueue

/**
 * 消息队列管理器
 */
class MsgQueueManager {


    private val sendMsgQueue by lazy { PriorityBlockingQueue<TcpProtocol>() }


    fun isEmpty(): Boolean {
        return sendMsgQueue.isEmpty() ?: return true
    }

    fun getNextMsg(): TcpProtocol? {
        if (sendMsgQueue.isEmpty()) {
            return null
        }

        return sendMsgQueue.poll()
    }


    fun pushMsg(tcpProtocol: TcpProtocol) {
        sendMsgQueue.add(tcpProtocol)
    }

}
