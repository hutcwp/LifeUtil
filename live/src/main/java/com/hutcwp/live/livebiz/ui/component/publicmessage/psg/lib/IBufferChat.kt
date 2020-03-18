package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

/**
 * @author RyanLee
 */
interface IBufferChat<D : BaseChatMsg> : Runnable {
    /**
     * 开始
     */
    fun play()

    /**
     * 添加公屏到缓冲区
     *
     * @param chatMsg D
     */
    fun addChat(chatMsg: D)

    /**
     * 添加公屏到缓冲区
     *
     * @param chatLists List
     */
    fun addChat(chatLists: List<D>)

    /**
     * 清空缓存区，更新
     *
     * @param chatLists
     */
    fun updateChat(chatLists: List<D>)

    /**
     * 释放资源
     */
    fun release()
}