package com.hutcwp.homepage

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.hutcwp.base.R
import java.util.*


/**
 *
 * Created by hutcwp on 2019-10-22 15:16
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class ViewAdapter : PagerAdapter {

    constructor(context: Context?, data: MutableList<PageItem>) : super() {
        this.mContext = context
        this.mData = data
    }

    //上下文
    private var mContext: Context? = null
    //数据
    private var mData: List<PageItem>? = null

    override fun isViewFromObject(view: View, ob1: Any): Boolean {
        return view == ob1
    }

    override fun getCount(): Int {
        return mData?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = View.inflate(mContext, R.layout.item_view, null)
        val ivImg = itemView.findViewById<ImageView>(R.id.ivImg)
        val tvPage = itemView.findViewById<TextView>(R.id.tvPage)
        val item = mData?.get(position)
        if (item?.page?.img?.isEmpty() != false) {
            val random = Random()
            val ranColor = -0x1000000 or random.nextInt(0x00ffffff)
            ivImg.setBackgroundColor(ranColor)
        } else {
            mContext?.let {
                Glide.with(it)
                        .load(item.page.img)
                        .transition(DrawableTransitionOptions().crossFade(200))
                        .into(ivImg)
            }
        }

        tvPage?.text = item?.page?.name
        itemView.setOnClickListener {
            Log.i(PageItemViewBinder.TAG, "path = ${item?.page?.path}")
            ARouter.getInstance().build(item?.page?.path).navigation()
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