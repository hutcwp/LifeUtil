package com.hutcwp.live.chat.intepreter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.hutcwp.live.R
import com.hutcwp.live.chat.bean.MyChatMsg
import com.ryan.baselib.util.AppUtils
import com.ryan.baselib.util.BitmapUtils
import com.ryan.baselib.util.DensityUtils
import com.ryan.baselib.widget.CenteredImageSpan

/**
 *  author : kevin
 *  date : 2021/11/6 1:32 AM
 *  description :
 */
class GiftInterpreter : IParseSpan<MyChatMsg> {


    override fun parse(data: MyChatMsg): Spannable {

        val strTo: String = AppUtils.getContext().resources.getString(R.string.str_to)
        val strSendTo: String = AppUtils.getContext().resources.getString(R.string.str_send_to)
        val builder = SpannableStringBuilder()
        if (data.headLight > 0) {
            // 设置头灯
            val resId: Int
            resId = when (data.headLight) {
                MyChatMsg.HEAD_LIGHT_VIP -> R.drawable.ic_vip
                MyChatMsg.HEAD_LIGHT_DIAMOND -> R.drawable.ic_diamond
                else -> -1
            }
            builder.append(" ")
            val imageNewSize: Int = DensityUtils.dp2px(AppUtils.getContext(), 24f)
            val vipSpan = CenteredImageSpan(AppUtils.getContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize))
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        builder.append(data.sendUserName).append(strTo).append(strSendTo).append(data.giftName)
        val leftLen = builder.length
        builder.append(" ").append(data.giftNum)
        val imageNewSize: Int = DensityUtils.dp2px(AppUtils.getContext(), 20f)
        val imageSpan = CenteredImageSpan(AppUtils.getContext(), BitmapUtils.decodeResToBitmap(data.giftRes, imageNewSize, imageNewSize))
        builder.setSpan(imageSpan, leftLen, leftLen + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val totalLen = builder.length
        // 设置送礼信息颜色
        var colorSpan: ForegroundColorSpan? = ForegroundColorSpan(Color.parseColor("#368CFD"))
        builder.setSpan(colorSpan, data.sendUserName.length + strTo.length, totalLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        //设置用户名颜色
        colorSpan = ForegroundColorSpan(ContextCompat.getColor(AppUtils.getContext(), R.color.color_chat_username))
        builder.setSpan(colorSpan, 0, data.sendUserName.length + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        return builder
    }


}