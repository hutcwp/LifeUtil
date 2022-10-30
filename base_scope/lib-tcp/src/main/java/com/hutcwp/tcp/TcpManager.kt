package com.hutcwp.tcp

import android.os.Looper
import com.hutcwp.event.AbstractTcpEvent
import com.hutcwp.tcp.protocol.TcpProtocol
import com.hutcwp.tcp.protocol.decodeToTcpRsp
import com.hutcwp.tcp.task.TcpServerRunnable
import me.hutcwp.log.MLog
import org.greenrobot.eventbus.EventBus
import java.net.Socket
import kotlin.concurrent.thread

/**
 *  author : kevin
 *  date : 2022/10/26 02:38
 *  description :
 */
object TcpManager : TcpRspCallBack {

    private const val TAG = "TcpManager"
    private const val IP_ADDRESS = "192.168.1.102"
    private const val PORT = 10002

    private val msgQueueManager by lazy { MsgQueueManager() }

    private val protocolEventList = mutableListOf<AbstractTcpEvent>()

    @Volatile
    var isConnected = false //当前连接状态


    /**
     * 初始化
     */
    fun initiation() {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            thread {
                connect()
            }
        } else {
            connect()
        }
    }

    private fun connect() {
        try {
            val socket = Socket(IP_ADDRESS, PORT)
            Thread(TcpServerRunnable(socket)).start()
            isConnected = true
            MLog.info(TAG, "tcp连接成功! socket=[${socket.hashCode()}]")
        } catch (e: Exception) {
            MLog.error(TAG, "连接异常，重试", e)
            Thread.sleep(3000L)
            connect()
        }
    }

    fun disConnect() {
        MLog.info(TAG, "tcp 断开连接")
        isConnected = false
        connect()
    }


    fun sendMsg(tcpProtocol: TcpProtocol) {
        msgQueueManager.pushMsg(tcpProtocol)
    }

    fun getNextSendMsg(): TcpProtocol? {
        return msgQueueManager.getNextMsg()
    }


    override fun onResponse(tcpProtocol: TcpProtocol) {
        MLog.info(TAG, "接收到协议：rsp=$tcpProtocol")

        protocolEventList.find {
            it.sid == tcpProtocol.sid && it.cid == tcpProtocol.cid
        }?.let {
            val constructor = it.javaClass.getConstructor()
            val event = constructor.newInstance()
            event.rspBean = tcpProtocol.decodeToTcpRsp()
            EventBus.getDefault().post(it)
            MLog.debug(TAG, "发送event = $event")
        }
    }

    fun registerEvent(abstractTcpEvent: AbstractTcpEvent) {
        protocolEventList.add(abstractTcpEvent)
    }

}


/**
 * tcp响应包回调
 */
interface TcpRspCallBack {
    fun onResponse(tcpProtocol: TcpProtocol)
}
