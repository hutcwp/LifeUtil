package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.BitmapUtils
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.CenteredImageSpan
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.GiftMsg
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder
import me.hutcwp.BasicConfig
import me.hutcwp.util.ResolutionUtils

/**
 *
 * Created by hutcwp on 2020-03-15 17:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GiftViewBinder : ItemViewBinder<GiftMsg, GiftViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_gift_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, data: GiftMsg) {
        val tips = holder.tips
        val strTo = tips.context.resources.getString(R.string.str_to)
        val strSendTo = tips.context.resources.getString(R.string.str_send_to)

        val builder = SpannableStringBuilder()
        if (data.headLight > 0) { // 设置头灯
            val resId: Int
            resId = when (data.headLight) {
                MyChatMsg.HEAD_LIGHT_VIP -> R.drawable.ic_vip
                MyChatMsg.HEAD_LIGHT_DIAMOND -> R.drawable.ic_diamond
                else -> return
            }
            builder.append(" ")
            val imageNewSize = ResolutionUtils.convertDpToPixel(24f, BasicConfig.getApplicationContext()).toInt()
            val vipSpan = CenteredImageSpan(BasicConfig.getApplicationContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize))
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        builder.append(data.sendUserName).append(strTo).append(strSendTo).append(data.giftName)
        val leftLen = builder.length
        builder.append(" ").append(data.giftNum)

        val imageNewSize = ResolutionUtils.convertDpToPixel(20f, BasicConfig.getApplicationContext()).toInt()
        val imageSpan = CenteredImageSpan(BasicConfig.getApplicationContext(), BitmapUtils.decodeResToBitmap(data.giftRes, imageNewSize, imageNewSize))
        builder.setSpan(imageSpan, leftLen, leftLen + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val totalLen = builder.length
        // 设置送礼信息颜色
        // 设置送礼信息颜色
        var colorSpan = ForegroundColorSpan(Color.parseColor("#368CFD"))
        builder.setSpan(colorSpan, data.sendUserName.length + strTo.length, totalLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        //设置用户名颜色
        //设置用户名颜色
        colorSpan = ForegroundColorSpan(ContextCompat.getColor(tips.context, R.color.color_chat_username))
        builder.setSpan(colorSpan, 0, data.sendUserName.length + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        tips.text = builder
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tv_gift_msg)
    }
}