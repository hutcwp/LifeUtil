package com.hutcwp.live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder

/**
 * Description:
 *
 * Created by n24314 on 2021/11/3. E-mail: caiwenpeng@corp.netease.com
 */
class DataType2ViewBinder : ItemViewBinder<Data, DataType2ViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_text, parent, false))
    }

    override fun onBindViewHolder(holder: DataType2ViewBinder.ViewHolder, item: Data) {
        holder.setTitle(item.title)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var titleView: TextView = itemView.findViewById(R.id.tv_text)

        fun setTitle(title: String) {
            titleView.text = title
        }
    }
}