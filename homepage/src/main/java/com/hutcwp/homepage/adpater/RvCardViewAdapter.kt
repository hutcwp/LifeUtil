package com.hutcwp.homepage.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hutcwp.homepage.PageItem
import com.hutcwp.homepage.R

/**
 *
 * Created by hutcwp on 2019-11-18 19:47
 *
 *
 *
 **/
class RvCardViewAdapter(private val mContext: Context, private val mData: MutableList<PageItem>) : RecyclerView.Adapter<RvCardViewAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return VH(root)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mData[position]

        holder.itemView.setBackgroundColor(item.bgColor)

        Glide.with(mContext)
                .load(item.page.img)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(holder.ivImg)

        holder.tvPage.text = item.page.name
        holder.itemView.setOnClickListener {
            ARouter.getInstance().build(item.page.path).navigation()
        }
    }


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImg: ImageView = itemView.findViewById(R.id.iv_cover)
        val tvPage: TextView = itemView.findViewById(R.id.tv_title)
    }

}