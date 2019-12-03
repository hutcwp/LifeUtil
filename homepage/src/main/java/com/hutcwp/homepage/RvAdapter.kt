package com.hutcwp.homepage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*

/**
 *
 * Created by hutcwp on 2019-11-18 19:47
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class RvAdapter(list: MutableList<PageItem>) : RecyclerView.Adapter<RvAdapter.VH>() {

    //数据
    private var mData: List<PageItem>? = null
    private var mContext: Context? = null

    init {
        mData = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return VH(root)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mData?.get(position)
        if (item?.page?.img?.isEmpty() != false) {
            val random = Random()
            val ranColor = -0x1000000 or random.nextInt(0x00ffffff)
            holder.ivImg.setBackgroundColor(ranColor)
        } else {
            mContext?.let {
                Glide.with(it)
                        .load(item.page.img)
                        .transition(DrawableTransitionOptions().crossFade(200))
                        .into(holder.ivImg)
            }
        }

        holder.tvPage?.text = item?.page?.name
        holder.itemView.setOnClickListener {
            Log.i(PageItemViewBinder.TAG, "path = ${item?.page?.path}")
            ARouter.getInstance().build(item?.page?.path).navigation()
        }
    }


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImg = itemView.findViewById<ImageView>(me.hutcwp.base.R.id.ivImg)
        val tvPage = itemView.findViewById<TextView>(me.hutcwp.base.R.id.tvPage)
    }

}