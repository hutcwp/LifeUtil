package com.hutcwp.live.chat.binder.msg.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.data.NormalMsgData

/**
 * 普通消息
 * @author RyanLee
 */
class NormalChatHolder : ItemViewBinder<NormalMsgData, NormalChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_normal_text, parent, false))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_normal_text_msg)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: NormalMsgData) {
        holder.text.text = item.spannable
    }

}