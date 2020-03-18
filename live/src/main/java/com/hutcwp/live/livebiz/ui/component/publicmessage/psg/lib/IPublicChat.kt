package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

/**
 *
 * Created by hutcwp on 2020-03-09 19:14
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IPublicChat<T> {
    fun addMessage(msg: T)
    fun addChatMessages(msgList: List<T>) //新增
    fun updatePublicMessages(msgList: List<T>) //更新
}