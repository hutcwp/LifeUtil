package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

/**
 * @author RyanLee
 */
interface ISimpleChat<D : BaseChatMsg?> {
    /**
     * 发送多条消息
     *
     * @param msgList List<D>
    </D> */
    fun sendMultiMsg(msgList: List<D>)

    /**
     * 发送单条消息
     *
     * @param msg D
     */
    fun sendSingleMsg(msg: D)

    /**
     * 更新聊天公屏
     *
     * @param msgList List<D>
    </D> */
    fun updateChatView(msgList: List<D>)

    /**
     * 单条新增
     */
    fun updateChatView(msg: D)

    /**
     * 释放资源
     */
    fun release()
}