package com.hutcwp.read.ui.home.adpter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hutcwp.read.R
import com.hutcwp.read.entitys.News
import com.hutcwp.read.util.WebUtils

/**
 * Created by hutcwp on 2017/4/14.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class NewsAdapter : BaseQuickAdapter<News, BaseViewHolder>(R.layout.read_item_read) {

    override fun convert(holder: BaseViewHolder, item: News) {
        holder.itemView.setOnClickListener {
            WebUtils.openInternal(context, item.url!!)
        }

        val title = String.format("%s. %s", holder.adapterPosition + 1, item.name)
        val description = "${item.updateTime} • ${item.from}"

        holder.setText(R.id.tv_read_name, title)
                .setText(R.id.tv_read_info, description)

        holder.getView<ImageView>(R.id.iv_read_icon).let {
            Glide.with(context)
                    .load(item.icon)
                    .into(it)
        }
    }

}
