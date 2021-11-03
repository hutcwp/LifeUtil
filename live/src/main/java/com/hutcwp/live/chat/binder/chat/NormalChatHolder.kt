package com.hutcwp.live.chat.binder.chat

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
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
import com.ryan.baselib.util.BitmapUtils
import com.ryan.baselib.util.DensityUtils
import com.ryan.baselib.widget.CenteredImageSpan

/**
 * 普通消息
 * @author RyanLee
 */
class NormalChatHolder : ItemViewBinder<MyChatMsg, NormalChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.live_layout_normal_text, parent, false))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_normal_text_msg)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MyChatMsg) {
        val data: MyChatMsg = item

        val builder = SpannableStringBuilder()
        if (data.headLight > 0) {
            // 设置头灯
            val resId: Int
            resId = when (data.headLight) {
                MyChatMsg.HEAD_LIGHT_VIP -> R.drawable.ic_vip
                MyChatMsg.HEAD_LIGHT_DIAMOND -> R.drawable.ic_diamond
                else -> return
            }
            builder.append(" ")
            val imageNewSize: Int = DensityUtils.dp2px(AppUtils.getContext(), 24f)
            val vipSpan = CenteredImageSpan(AppUtils.getContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize))
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        builder.append(data.sendUserName).append(AppUtils.getContext().getResources().getString(R.string.str_to))

        // 用户名的长度
        val leftLen = builder.length
        if (!TextUtils.isEmpty(data.atUserName)) {
            builder.append(AppUtils.getContext().getResources().getString(R.string.str_at)).append(data.atUserName).append(" ")
        }
        // 用户名到@的长度
        val nLeftLen = builder.length
        builder.append(data.content)
        //设置@用户名的颜色
        var colorSpan: ForegroundColorSpan? = ForegroundColorSpan(ContextCompat.getColor(AppUtils.getContext(), R.color.color_chat_at_username))
        builder.setSpan(colorSpan, leftLen, nLeftLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        //设置用户名颜色
        colorSpan = ForegroundColorSpan(ContextCompat.getColor(AppUtils.getContext(), R.color.color_chat_username))
        builder.setSpan(colorSpan, 1, data.sendUserName.length + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        holder.text.text = builder
    }

}