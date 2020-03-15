package com.hutcwp.live.livebiz.ui.component.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.bean.Playable
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder

/**
 *Author:Administrator
 *Time:2019/6/3 22:55
 *YY:909076244
 **/

/**
 * @author Drakeet Xu
 */
class PageItemViewBinder(var context: Context) : ItemViewBinder<Playable, PageItemViewBinder.PageHolder>() {

    private var onItemClick: OnItemClick? = null

    override fun onBindViewHolder(holder: PageHolder, item: Playable) {
//        Glide.with(context)
//                .load(item.page.img)
//                .transition(DrawableTransitionOptions().crossFade(200))
//                .into(holder.img)
//
        holder.text.text = (item.name)
        holder.itemView.setOnClickListener {
            onItemClick?.onItemClick(item)
        }
    }

    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): PageHolder {
        return PageHolder(inflater.inflate(R.layout.item_public_msg, parent, false))
    }


    override fun onViewDetachedFromWindow(holder: PageHolder) {
        holder.itemView.clearAnimation()
    }

    fun setOnItemClick(l: OnItemClick) {
        onItemClick = l
    }

    interface OnItemClick {
        fun onItemClick(playable: Playable);
    }

    companion object {
        const val TAG = "PageItemViewBinder"
    }
}