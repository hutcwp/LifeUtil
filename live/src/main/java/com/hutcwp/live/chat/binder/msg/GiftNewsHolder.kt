package com.hutcwp.live.chat.binder.msg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.data.GiftMsgData

/**
 * 送礼的Holder
 *
 * @author RyanLee
 */
class GiftNewsHolder : ItemViewBinder<GiftMsgData, GiftNewsHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_gift_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, msgData: GiftMsgData) {
        holder.tips.text = msgData.spannable
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_gift_msg)
    }
}