package com.hutcwp.live.chat.binder.msg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.data.ActivityNewsData

/**
 * @author RyanLee
 */
class ActivityNewsHolder : ItemViewBinder<ActivityNewsData, ActivityNewsHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_activity_news, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ActivityNewsData) {
        holder.textView.text = item.spannable
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_activity_news)
    }
}