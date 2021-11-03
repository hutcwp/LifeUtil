package com.hutcwp.live.chat.binder.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.bean.MyChatMsg

/**
 * 头部信息
 * @author RyanLee
 */
class HeaderChatHolder: ItemViewBinder<MyChatMsg, HeaderChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_header_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MyChatMsg) {
        holder.tips.setText("健康生活")

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_header_text_msg)
    }

}