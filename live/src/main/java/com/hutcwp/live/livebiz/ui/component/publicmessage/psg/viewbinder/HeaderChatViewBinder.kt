package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.HeaderChatMsg
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by hutcwp on 2020-03-15 17:59
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class HeaderChatViewBinder : ItemViewBinder<HeaderChatMsg, HeaderChatViewBinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_header_text, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, item: HeaderChatMsg) {
        holder.tips.text = holder.tips.context.resources.getString(R.string.test_healthy_live)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_header_text_msg)
    }

}