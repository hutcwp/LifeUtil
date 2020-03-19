package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.ActivityNewsMsg
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder
import me.hutcwp.BasicConfig
import me.hutcwp.util.ResolutionUtils

/**
 *
 * Created by hutcwp on 2020-03-15 17:56
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class ActivityNewsViewBinder : ItemViewBinder<ActivityNewsMsg, ActivityNewsViewBinder.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, data: ActivityNewsMsg) {
        val textView = holder.textView
        val drawableLeft = ContextCompat.getDrawable(textView.context, R.drawable.ic_activity)
        val drawableRight = ContextCompat.getDrawable(textView.context, R.drawable.ic_arrow)

        val int18 = ResolutionUtils.convertDpToPixel(18f, BasicConfig.getApplicationContext()).toInt()
        val dp12 = ResolutionUtils.convertDpToPixel(12f, BasicConfig.getApplicationContext()).toInt()
        drawableLeft?.setBounds(0, 0, int18, int18)
        drawableRight?.setBounds(0, 0, dp12, dp12)
        textView.setCompoundDrawables(drawableLeft, null, drawableRight, null)
        textView.text = data.activityNews
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_activity_news, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_activity_news)
    }
}