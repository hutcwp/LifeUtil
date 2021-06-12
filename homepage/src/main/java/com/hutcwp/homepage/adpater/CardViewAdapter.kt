package com.hutcwp.homepage.adpater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hutcwp.homepage.PageItem
import me.hutcwp.base.R


/**
 *
 * Created by hutcwp on 2019-10-22 15:16
 *
 *  首页卡片适配器
 *
 **/
class CardViewAdapter(val context: Context, val data: MutableList<PageItem>) : PagerAdapter() {


    override fun isViewFromObject(view: View, ob1: Any): Boolean {
        return view == ob1
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = View.inflate(context, R.layout.item_view, null)
        val ivImg = itemView.findViewById<ImageView>(R.id.iv_cover)
        val tvPage = itemView.findViewById<TextView>(R.id.tv_title)

        val item = data[position]

        tvPage?.text = item.page.name

        ivImg.setBackgroundColor(item.bgColor)

        Glide.with(context)
                .load(item.page.img)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(ivImg)

        itemView.setOnClickListener {
            ARouter.getInstance().build(item.page.path).navigation()
        }

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

//    override fun getPageWidth(position: Int): Float {
//        return 0.8f
//    }

}