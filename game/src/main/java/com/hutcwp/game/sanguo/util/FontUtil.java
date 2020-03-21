package com.hutcwp.game.sanguo.util;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

/**
 * Created by hutcwp on 2019-06-24 21:06
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class FontUtil {

    public static void setFont(TextView view, int fontRes) {
        if (view != null) {
            Typeface typeface = ResourcesCompat.getFont(view.getContext(), fontRes);
            view.setTypeface(typeface);
        }
    }
}
