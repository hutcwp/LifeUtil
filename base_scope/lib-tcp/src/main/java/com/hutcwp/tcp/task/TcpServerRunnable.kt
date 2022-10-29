package com.hutcwp.tcp.task

import me.hutcwp.log.MLog
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

private const val TAG = "ServerThread"

/**
 * tcp服务任务
 */
class TcpServerRunnable(private val socket: Socket) : Runnable {


    lateinit var bw: BufferedWriter
    lateinit var br: BufferedReader


    override fun run() {
        MLog.info(TAG, "服务端:[${socket.inetAddress.hostAddress}] 已连接")

        bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        br = BufferedReader(InputStreamReader(socket.getInputStream()))

        //身份认证
        bw.write("auth:android\n") //向服务器端发送一条消息
        bw.flush()

        println("发送消息给服务器：")

        val msg = br.readLine()

        MLog.info(TAG, "收到服务器消息：$msg")

        if (msg.endsWith("auth success!")) {
            MLog.info(TAG, "身份校验成功～可以安全通信了")
        } else {
            MLog.info(TAG, "身份校验失败～")
            return
        }

        Thread(ReceiveMsgRunnable(br)).start()
        Thread(SendMsgRunnable(bw)).start()
        Thread(BeatHeartRunnable(bw)).start()

        MLog.info(TAG, "服务端[${socket.inetAddress.hostAddress}]的连接断开。。。")

    }

}
