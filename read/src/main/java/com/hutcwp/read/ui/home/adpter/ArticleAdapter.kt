package com.hutcwp.read.ui.home.adpter

import android.text.Html
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hutcwp.read.R
import com.hutcwp.read.entitys.News
import com.hutcwp.read.http.ApiFactory
import com.hutcwp.read.util.WebUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog

/**
 * Created by hutcwp on 2017/4/14.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class ArticleAdapter : BaseQuickAdapter<News, BaseViewHolder>(R.layout.read_item_read) {

    override fun convert(holder: BaseViewHolder, item: News) {

        holder.itemView.setOnClickListener {
            MLog.info("ArticleAdapter", "item.url = ${item.url}")
//            val url = "https://gank.io/api/v2/post/${item.url}"
//            val url = "https://gank.io/post/${item.url}"
            GlobalScope.launch {
                val articleDetailBean = withContext(Dispatchers.IO) {
                    ApiFactory.getGirlsController().getArticleDetail(item.url!!)
                }
                val html = Html.fromHtml(articleDetailBean.data.markdown)
                WebUtils.load(context, html.toString())
            }
        }
        //将标题设置为 序号.内容这种格式
        val title = String.format("%s. %s", holder.adapterPosition + 1, item.name)
        val info = "${item.updateTime} • ${item.from}"

        holder.setText(R.id.tv_read_name, title)
                .setText(R.id.tv_read_info, info)

        holder.getView<ImageView>(R.id.iv_read_icon).let {
            Glide.with(context)
                    .load(item.icon)
                    .into(it)
        }
    }

}
