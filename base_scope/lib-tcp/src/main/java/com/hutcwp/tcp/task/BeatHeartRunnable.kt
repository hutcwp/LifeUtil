package com.hutcwp.tcp.task

import com.hutcwp.tcp.TcpManager
import com.hutcwp.tcp.protocol.getHeartTcpProtocol
import com.hutcwp.tcp.transform.TcpTransformUtil
import me.hutcwp.log.MLog
import java.io.BufferedWriter

/**
 *  author : kevin
 *  date : 2022/10/29 17:02
 *  description :
 *  发送心跳包
 */
private const val TAG = "BeatHeartRunnable"
private const val BEAT_DURATION = 15000L
private const val MAX_RETRY_COUNT = 1

class BeatHeartRunnable(private val bw: BufferedWriter) : Runnable {

    private var retryCount = 0

    @Volatile
    private var disConnected = false

    override fun run() {
        while (!disConnected) {
            try {
                sendHeartMsg()
                Thread.sleep(BEAT_DURATION)
                retryCount = 0
            } catch (e: Exception) {
                MLog.warn(TAG, "发送心跳消息失败，$e", e)
                if (retryCount >= MAX_RETRY_COUNT) {
                    reconnectTcp()
                }

                retryCount++
                MLog.debug(TAG, "重试次数增加[retryCount=$retryCount]")
            }
        }
    }

    private fun reconnectTcp() {
        try {
            bw.close()
        } catch (e: Exception) {
            MLog.warn(TAG, "关闭bw流失败", e)
        }

        TcpManager.disConnect()
        disConnected = true
    }

    private fun sendHeartMsg() {
        val content = TcpTransformUtil.transToData(getHeartTcpProtocol()).data
        bw.write(content)
        bw.flush()

        MLog.info(TAG, "发送心跳消息给服务端---->$content")
    }
}