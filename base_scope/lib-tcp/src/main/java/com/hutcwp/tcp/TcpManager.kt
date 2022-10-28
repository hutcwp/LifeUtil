package com.hutcwp.tcp

import java.net.Socket
import kotlin.concurrent.thread

/**
 *  author : kevin
 *  date : 2022/10/26 02:38
 *  description :
 */
object TcpManager {


    fun connect() {
        thread {
            val socket = Socket("192.168.1.102", 10002)
            ServerThread(socket).start()
        }
    }

}