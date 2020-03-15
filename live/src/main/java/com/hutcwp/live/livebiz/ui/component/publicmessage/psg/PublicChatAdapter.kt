package com.hutcwp.live.livebiz.ui.component.publicmessage.psg

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatAdapter
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatViewHolder
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.holder.*
import com.hutcwp.livebiz.R
import me.hutcwp.BasicConfig
import me.hutcwp.log.MLog

/**
 *
 * Created by hutcwp on 2020-03-09 19:12
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */

class PublicChatAdapter : BaseChatAdapter<BaseChatMsg>() {

    private val TYPE_HEADER = -1000
    private val mDatas: MutableList<BaseChatMsg> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseChatViewHolder {
        MLog.info(TAG, "viewType is $viewType")
        return when (viewType) {
            MyChatMsg.TYPE_NORMAL_TEXT -> NormalChatHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_normal_text, parent, false))
            MyChatMsg.TYPE_SYSTEM_NEWS -> SystemNewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_system_news_text, parent, false))
            MyChatMsg.TYPE_GIFT_MSG -> GiftNewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_gift_text, parent, false))
            MyChatMsg.TYPE_ACTIVITY_NEWS -> ActivityNewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_activity_news, parent, false))
            TYPE_HEADER -> HeaderChatHolder(LayoutInflater.from(BasicConfig.getApplicationContext()).inflate(R.layout.layout_header_text, parent, false))
            else -> HeaderChatHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_header_text, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val msg = mDatas[position]
        if (msg is MyChatMsg) {
            return msg.type
        }

        return MyChatMsg.TYPE_NORMAL_TEXT
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: BaseChatViewHolder, position: Int) {
        if (mDatas.size >= position) {
            holder.bindData(mDatas[position], position)
        }
    }

    override fun addItem(chatMsg: BaseChatMsg) {
        mDatas.add(chatMsg)
        notifyItemChanged(itemCount)
    }

    override fun addItemList(dataList: List<BaseChatMsg>) {
        mDatas.addAll(dataList.toList())
        notifyDataSetChanged()
    }

    override fun removeItems(startPos: Int, endPos: Int) {
    }

    companion object {
        private const val TAG = "PublicChatAdapter"
    }
}