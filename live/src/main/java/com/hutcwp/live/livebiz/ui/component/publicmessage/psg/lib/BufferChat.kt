package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

import android.os.Handler
import android.os.Looper
import java.util.*

/**
 * 公屏缓冲池
 *
 * @author RyanLee
 */
class BufferChat<D : BaseChatMsg>() : IBufferChat<D> {

    private val mUIHandler = Handler(Looper.getMainLooper())
    private var mBufferQueue: Queue<D> = LinkedList()
    private var mSimpleChat: ISimpleChat<D>? = null
    private var mUpdateTime = 0


    constructor(iSimpleChat: ISimpleChat<D>, duration: Int) : this() {
        mSimpleChat = iSimpleChat
        mUpdateTime = duration
    }

    override fun play() {
        mUIHandler.removeCallbacks(this)
        mUIHandler.post(this)
    }

    override fun addChat(chatMsg: D) {
        synchronized(LOCK) { mBufferQueue.add(chatMsg) }
    }

    override fun addChat(chatLists: List<D>) {
        if (chatLists.isEmpty()) {
            return
        }
        synchronized(LOCK) { mBufferQueue.addAll(chatLists) }
    }

    override fun updateChat(chatLists: List<D>) {
        synchronized(LOCK) {
            mBufferQueue.clear()
            mBufferQueue.addAll(chatLists)
        }
    }

    override fun release() {
        mUIHandler.removeCallbacks(this)
        mBufferQueue.clear()
    }

    override fun run() {
        if (mBufferQueue.isNullOrEmpty()) {
            mUIHandler.removeCallbacks(this)
            mUIHandler.postDelayed(this, mUpdateTime.toLong())
            return
        }
        synchronized(LOCK) {
            if (mBufferQueue.isNotEmpty()) {
                val msg = mBufferQueue.remove()
                mSimpleChat?.updateChatView(msg)
            }
        }
        mUIHandler.removeCallbacks(this)
        mUIHandler.postDelayed(this, mUpdateTime.toLong())
    }


    companion object {
        private val LOCK = Any()
        private const val CROW_SIZE = 50
    }
}