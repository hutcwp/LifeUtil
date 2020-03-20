package com.hutcwp.live.livebiz.ui.component.danmu.kinds

import android.graphics.Canvas
import android.text.*
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.hutcwp.live.livebiz.base.util.BasicConfig
import com.hutcwp.live.livebiz.ui.component.emoji.RichTextManager
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.BitmapUtils
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.CenteredImageSpan
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import com.hutcwp.livebiz.R
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer
import me.hutcwp.util.ResolutionUtils
import java.util.*
import kotlin.math.ceil

class ColorTextCacheStuffer : SimpleTextCacheStuffer() {

    override fun measure(danmaku: BaseDanmaku?, paint: TextPaint?, fromWorkerThread: Boolean) {
        if (danmaku != null && danmaku is ColorDanmaku) {
            danmaku.paintHeight = 100f
            val width: Float = if (danmaku.msg != null) {
                paint?.measureText(getSpannableStr(danmaku.msg!!).toString()) ?: 0f
            } else {
                0f
            }
            danmaku.paintWidth = width + 20
        }
    }

    override fun drawText(
            danmaku: BaseDanmaku,
            lineText: String?,
            canvas: Canvas?,
            left: Float,
            top: Float,
            paint: TextPaint?,
            fromWorkerThread: Boolean
    ) {
        if (paint == null) {
            return
        }

        if (danmaku is ColorDanmaku) {

            val data = danmaku.msg

            data?.apply {
                val builder = getSpannableStr(data)
                val staticLayout = StaticLayout(builder, paint, ceil(StaticLayout.getDesiredWidth(builder, paint).toDouble()).toInt(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true)
                staticLayout.draw(canvas)
            }
        } else {
            super.drawText(danmaku, lineText, canvas, left, top, paint, fromWorkerThread)
        }
    }

    private fun getSpannableStr(data: MyChatMsg): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        if (data.headLight > 0) { // 设置头灯
            val resId: Int = when (data.headLight) {
                MyChatMsg.HEAD_LIGHT_VIP -> R.drawable.ic_vip
                MyChatMsg.HEAD_LIGHT_DIAMOND -> R.drawable.ic_diamond
                else -> 0
            }
            builder.append(" ")
            val imageNewSize = ResolutionUtils.convertDpToPixel(25f, me.hutcwp.BasicConfig.getApplicationContext()).toInt()
            val vipSpan = CenteredImageSpan(me.hutcwp.BasicConfig.getApplicationContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize))
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        builder.append(data.sendUserName).append(me.hutcwp.BasicConfig.getApplicationContext().resources.getString(R.string.str_to))

        val leftLen = builder.length
        if (!TextUtils.isEmpty(data.atUserName)) {
            builder.append(me.hutcwp.BasicConfig.getApplicationContext().resources.getString(R.string.str_at)).append(data.atUserName).append(" ")
        }

        // 用户名到@的长度
        val nLeftLen = builder.length
        builder.append(data.content)

        //设置@用户名的颜色
        var colorSpan = ForegroundColorSpan(ContextCompat.getColor(me.hutcwp.BasicConfig.getApplicationContext(), R.color.color_chat_at_username))
        builder.setSpan(colorSpan, leftLen, nLeftLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        //设置用户名颜色
        colorSpan = ForegroundColorSpan(ContextCompat.getColor(me.hutcwp.BasicConfig.getApplicationContext(), R.color.color_chat_username))
        val nameLength = data.sendUserName?.length ?: 0
        builder.setSpan(colorSpan, 1, nameLength + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        val features: MutableList<RichTextManager.Feature> = ArrayList()
        features.add(RichTextManager.Feature.EMOTICON)
        RichTextManager.getInstance().getSpannableString(BasicConfig.getInstance().appContext, builder, features)
        return builder
    }
}