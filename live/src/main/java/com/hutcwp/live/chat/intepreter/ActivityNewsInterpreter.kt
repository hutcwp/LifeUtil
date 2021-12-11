package com.hutcwp.live.chat.intepreter

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import com.hutcwp.live.R
import com.hutcwp.live.chat.bean.MyChatMsg
import com.ryan.baselib.util.AppUtils
import com.ryan.baselib.util.DensityUtils
import java.lang.StringBuilder

/**
 *  author : kevin
 *  date : 2021/11/6 2:32 AM
 *  description :
 */
class ActivityNewsInterpreter : IParseSpan<MyChatMsg> {

    override fun parse(data: MyChatMsg): Spannable {
        val builder = SpannableStringBuilder()
        val drawableLeft: Drawable? = ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.ic_activity)
        val drawableRight: Drawable? = ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.ic_arrow)
        drawableLeft?.setBounds(0, 0, DensityUtils.dp2px(AppUtils.getContext(), 18f), DensityUtils.dp2px(AppUtils.getContext(), 18f))
        drawableRight?.setBounds(0, 0, DensityUtils.dp2px(AppUtils.getContext(), 12f), DensityUtils.dp2px(AppUtils.getContext(), 12f))
        builder.append(data.content)
        return builder
    }
}