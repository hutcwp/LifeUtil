package com.hutcwp.live.livebiz.ui.component.publicmessage.lib

/**
 *
 * Created by hutcwp on 2020-03-09 19:14
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IPublicChat<T> {

    fun addMsg(msg: T)
    fun addMsgs(msgs: List<T>)
    fun updateMsgs(msgs: List<T>)

}