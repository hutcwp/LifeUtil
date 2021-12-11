package com.hutcwp.live.chat.binder.msg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.data.NormalMsgData

/**
 *  author : kevin
 *  date : 2021/11/6 1:34 PM
 *  description : 普通消息，只有一个spannable
 */
class NormalMsgHolder : ItemViewBinder<NormalMsgData, NormalMsgHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_normal_text, parent, false))
    }

    override fun onBindViewHolder(holder: NormalMsgHolder.ViewHolder, item: NormalMsgData) {
        holder.msg.text = item.spannable
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msg: TextView = itemView.findViewById(R.id.tv_normal_text_msg)
    }
}