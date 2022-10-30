package com.hutcwp.tcp.task

import com.hutcwp.tcp.TcpManager
import com.hutcwp.tcp.protocol.TransformData
import com.hutcwp.tcp.transform.TcpTransformUtil
import me.hutcwp.log.MLog
import java.io.BufferedReader

/**
 *  author : kevin
 *  date : 2022/10/29 16:45
 *  description :
 *  接受消息
 */
private const val TAG = "ReceiveMsgRunnable"

class ReceiveMsgRunnable(private val br: BufferedReader) : Runnable {

    @Volatile
    private var flag = true

    override fun run() {
        receiveMsg()
    }

    private fun receiveMsg() {
        while (flag) {
            try {
                //读取客户端发送来的消息
                var line: String
                var content = StringBuilder()
                while (br.readLine().also { line = it } != null) {
                    val isStartFlag = line.startsWith("start:")
                    val isEndFlag = line.startsWith("end:")

                    if (isStartFlag) {
                        content = StringBuilder()
                        MLog.info(TAG, "开始解析一个包")
                    }

                    content.append(line)
                    MLog.info(TAG, "line=$line")

                    if (isEndFlag) {
                        MLog.info(TAG, "结束解析一个包")
                        MLog.info(TAG, "收到服务器消息：$content")

                        val dataMsg = content.toString()
                        content = StringBuilder()

                        TcpTransformUtil.transToProtocol(TransformData(dataMsg)).let {
                            TcpManager.onResponse(it)
                        }
                    }
                }

                MLog.info(TAG, "服务端输入流断开")
            } catch (e: Exception) {
                MLog.info(TAG, "接收服务端的消息异常：$e", e)
                if (!TcpManager.isConnected) {
                    flag = false
                }
            }
        }
    }

}