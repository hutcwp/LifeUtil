package com.hutcwp.tcp


import android.util.Log
import com.hutcwp.tcp.protocol.TcpProtocol
import com.hutcwp.tcp.protocol.TransformData
import com.hutcwp.tcp.protocol.decodeToTcpRsp
import com.hutcwp.tcp.transform.TcpTransformUtil
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

private const val TAG = "ServerThread"

class ServerThread(private val client: Socket) : Thread() {

    private val sendMsgQueue = LinkedBlockingQueue<TcpProtocol>()

    lateinit var bw: BufferedWriter
    lateinit var br: BufferedReader


    override fun run() {
        Log.i(TAG, "服务端:" + client.inetAddress.hostAddress + "已连接到服务器")

        bw = BufferedWriter(OutputStreamWriter(client.getOutputStream()))
        bw.write("auth:android\n") //向服务器端发送一条消息
        bw.flush()

        println("发送消息给服务器：")

        br = BufferedReader(InputStreamReader(client.getInputStream()))
        val msg = br.readLine()

        println("收到服务器消息：$msg")

        if (msg.endsWith("auth success!")) {
            println("身份校验成功～可以安全通信了")
        } else {
            println("身份校验失败～")
            return
        }


        MsgQueueManager.registerSocket(sendMsgQueue)

        thread {
            receiveMsg()
        }

        thread {
            sendMsg()
        }

        println("服务端${client.inetAddress.hostAddress}的连接断开。。。")
    }

    private fun receiveMsg() {

        while (true) {
            try {
                //读取客户端发送来的消息
                var line: String
                var content = StringBuilder()
                while (br.readLine().also { line = it } != null) {

                    val isStartFlag = line.startsWith("start:")
                    val isEndFlag = line.startsWith("end:")

                    if (isStartFlag) {
                        content = StringBuilder()
                        println("开始解析一个包")
                    }

                    content.append(line)
                    println("line=$line")


                    if (isEndFlag) {
                        println("结束解析一个包")
                        println("收到服务器消息：$content")

                        val dataMsg = content.toString()
                        content = StringBuilder()
                        TcpTransformUtil.transToProtocol(TransformData(dataMsg)).let {
                            MsgQueueManager.onResponse(it.decodeToTcpRsp())
                        }
                    }
                }

                println("服务端输入流断开")
            } catch (e: Exception) {
                println("接收服务端的消息异常：$e")
            }
        }
    }


    private fun sendMsg() {
        while (true) {
            try {
                val bw = BufferedWriter(OutputStreamWriter(client.getOutputStream()))
                while (sendMsgQueue.isNotEmpty()) {
                    val tcpProtocol = sendMsgQueue.poll()
                    val content = TcpTransformUtil.transToData(tcpProtocol).data
                    bw.write(content)
                    bw.flush()

                    println("主动发送消息给服务端---->$content")
                }
            } catch (e: Exception) {
                println("发送服务端的消息异常：$e")
            }
        }
    }
}
