package com.hutcwp.tcp.task

import com.hutcwp.tcp.MsgQueueManager
import com.hutcwp.tcp.TcpManager
import com.hutcwp.tcp.transform.TcpTransformUtil
import me.hutcwp.log.MLog
import java.io.BufferedWriter

/**
 *  author : kevin
 *  date : 2022/10/29 16:50
 *  description : 发送消息
 */
private const val TAG = "SendMsgRunnable"

class SendMsgRunnable(private val bw: BufferedWriter) : Runnable {

    override fun run() {
        try {
            while (true) {
                val tcpProtocol = TcpManager.getNextSendMsg()?.let {
                    val content = TcpTransformUtil.transToData(it).data
                    bw.write(content)
                    bw.flush()

                    MLog.info(TAG, "主动发送消息给服务端---->$content")
                }
            }
        } catch (e: Exception) {
            MLog.info(TAG, "发送服务端的消息异常：$e", e)
        }
    }

}