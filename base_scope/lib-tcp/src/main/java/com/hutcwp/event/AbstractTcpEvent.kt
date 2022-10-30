package com.hutcwp.event

import com.hutcwp.tcp.response.TcpRspBean

/**
 *  author : kevin
 *  date : 2022/10/29 22:15
 *  description :
 */
abstract class AbstractTcpEvent() {

    var rspBean: TcpRspBean? = null

    //大类
    abstract val sid: Int

    //小类
    abstract val cid: Int

}