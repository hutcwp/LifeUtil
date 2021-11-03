package com.hutcwp.live.chat.binder.chat

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.hutcwp.live.R
import com.hutcwp.live.chat.bean.MyChatMsg
import com.ryan.baselib.util.AppUtils
import com.ryan.baselib.util.DensityUtils

/**
 * @author RyanLee
 */
class ActivityNewsHolder: ItemViewBinder<MyChatMsg, ActivityNewsHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_activity_news, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MyChatMsg) {
        val drawableLeft: Drawable? = ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.ic_activity)
        val drawableRight: Drawable? = ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.ic_arrow)
        drawableLeft?.setBounds(0, 0, DensityUtils.dp2px(AppUtils.getContext(), 18f), DensityUtils.dp2px(AppUtils.getContext(), 18f))
        drawableRight?.setBounds(0, 0, DensityUtils.dp2px(AppUtils.getContext(), 12f), DensityUtils.dp2px(AppUtils.getContext(), 12f))
        holder.textView.setCompoundDrawables(drawableLeft, null, drawableRight, null)
        holder.textView.text = item.activityNews

    }


    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_activity_news)
    }
}