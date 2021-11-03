package com.hutcwp.live

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.ItemViewDelegate

/**
 * Description:
 *
 * Created by n24314 on 2021/11/2. E-mail: caiwenpeng@corp.netease.com
 */
class FooViewDelegate: ItemViewBinder<Foo, FooViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Foo) {
        holder.fooView.text = item.value

    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val fooView: TextView = itemView.findViewById(R.id.tv_text)
    }

}