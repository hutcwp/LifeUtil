package com.hutcwp.live.livebiz.ui.component.emoji.filter;

import android.content.Context;
import android.text.Spannable;

/**
 * Created by 张宇 on 2017/11/15.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
public interface RichTextFilter {

    void parseSpannable(Context context, Spannable spannable, int maxWidth);

    void parseSpannable(Context context, Spannable spannable, int maxWidth, int normal);

    void parseSpannable(Context context, Spannable spannable, int maxWidth, Object tag);
}
