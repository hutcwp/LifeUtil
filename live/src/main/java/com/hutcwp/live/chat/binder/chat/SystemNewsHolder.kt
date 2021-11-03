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
 * 系统消息
 * @author RyanLee
 */
class SystemNewsHolder : ItemViewBinder<MyChatMsg, SystemNewsHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_system_news_text, parent, false))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_system_news_msg)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MyChatMsg) {
        holder.tips.setText(item.systemNews)
    }

}