package com.hutcwp.tcp.protocol

import com.google.gson.Gson
import com.hutcwp.tcp.response.TcpRspBean

/**
 *  author : kevin
 *  date : 2022/10/29 03:40
 *  description :
 */

/**
 * 传输层用的data，
 */
data class TransformData(val data: String) : java.io.Serializable {

}

/**
 * tcp协议格式
 * sid cid 协议号
 */
data class TcpProtocol(val sid: Int, val cid: Int, val content: String) {

}

/**
 * 将协议的content字段转换成data Bean
 */
fun TcpProtocol.decodeToTcpRsp(): TcpRspBean {
    return Gson().fromJson(this.content, TcpRspBean::class.java)
}