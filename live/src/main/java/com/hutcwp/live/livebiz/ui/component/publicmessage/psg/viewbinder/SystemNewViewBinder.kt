package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.SystemNewMsg
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder

/**
 *
 * Created by hutcwp on 2020-03-15 18:03
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class SystemNewViewBinder : ItemViewBinder<SystemNewMsg, SystemNewViewBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: SystemNewMsg) {
        holder.tips.text = item.systemNews
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_system_news_text, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_system_news_msg)
    }
}