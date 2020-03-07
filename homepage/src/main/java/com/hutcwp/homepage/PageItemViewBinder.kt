package com.hutcwp.homepage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.drakeet.multitype.ItemViewBinder

/**
 *Author:Administrator
 *Time:2019/6/3 22:55
 *YY:909076244
 **/

/**
 * @author Drakeet Xu
 */
class PageItemViewBinder(var context: Context) : ItemViewBinder<PageItem, PageItemViewBinder.PageHolder>() {
    override fun onBindViewHolder(holder: PageHolder, item: PageItem) {
        Glide.with(context)
                .load(item.page.img)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(holder.img)

        holder.text.text = (item.page.name)
        holder.itemView.setOnClickListener {
            Log.i(TAG, "path = ${item.page.path}")
            ARouter.getInstance().build(item.page.path).navigation()
        }
    }

    private var lastShownAnimationPosition: Int = 0

    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.name)
        val img: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): PageHolder {
        return PageHolder(inflater.inflate(R.layout.hp_item_page, parent, false))
    }

//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(holder: TextHolder, item: TextItem) {
//        holder.text.text = "hello: " + item.text
//        // should show animation, ref: https://github.com/drakeet/MultiType/issues/149
//        setAnimation(holder.itemView, holder.adapterPosition)
//    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastShownAnimationPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left))
            lastShownAnimationPosition = position
        }
    }

    override fun onViewDetachedFromWindow(holder: PageHolder) {
        holder.itemView.clearAnimation()
    }

    companion object {
        const val TAG = "PageItemViewBinder"
    }
}