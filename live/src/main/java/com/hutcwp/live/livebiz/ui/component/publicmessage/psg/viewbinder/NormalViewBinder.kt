package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder

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
import com.hutcwp.live.livebiz.ui.component.emoji.RichTextManager
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.BitmapUtils
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.CenteredImageSpan
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.NormalMsg
import com.hutcwp.livebiz.R
import me.drakeet.multitype.ItemViewBinder
import me.hutcwp.BasicConfig
import me.hutcwp.util.ResolutionUtils
import java.util.*

/**
 *
 * Created by hutcwp on 2020-03-13 17:52
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class NormalViewBinder : ItemViewBinder<NormalMsg, NormalViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_normal_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, data: NormalMsg) {
        val text = holder.fooView
        val builder = SpannableStringBuilder()
        if (data.headLight > 0) { // 设置头灯
            val resId: Int
            resId = when (data.headLight) {
                MyChatMsg.HEAD_LIGHT_VIP -> R.drawable.ic_vip
                MyChatMsg.HEAD_LIGHT_DIAMOND -> R.drawable.ic_diamond
                else -> return
            }
            builder.append(" ")
            val imageNewSize = ResolutionUtils.convertDpToPixel(25f, BasicConfig.getApplicationContext()).toInt()
            val vipSpan = CenteredImageSpan(BasicConfig.getApplicationContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize))
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        builder.append(data.sendUserName).append(BasicConfig.getApplicationContext().resources.getString(R.string.str_to))

        // 用户名的长度
        val leftLen = builder.length
        if (!TextUtils.isEmpty(data.atUserName)) {
            builder.append(BasicConfig.getApplicationContext().resources.getString(R.string.str_at)).append(data.atUserName).append(" ")
        }
        // 用户名到@的长度
        // 用户名到@的长度
        val nLeftLen = builder.length
        builder.append(data.content)
        //设置@用户名的颜色
        //设置@用户名的颜色
        var colorSpan = ForegroundColorSpan(ContextCompat.getColor(BasicConfig.getApplicationContext(), R.color.color_chat_at_username))
        builder.setSpan(colorSpan, leftLen, nLeftLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        //设置用户名颜色
        //设置用户名颜色
        colorSpan = ForegroundColorSpan(ContextCompat.getColor(BasicConfig.getApplicationContext(), R.color.color_chat_username))
        builder.setSpan(colorSpan, 1, data.sendUserName.length + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        val features: MutableList<RichTextManager.Feature> = ArrayList()
        features.add(RichTextManager.Feature.EMOTICON)
        RichTextManager.getInstance().getSpannableString(text.context, builder, features)

        text.text = builder
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fooView: TextView = itemView.findViewById(R.id.tv_normal_text_msg)
    }
}