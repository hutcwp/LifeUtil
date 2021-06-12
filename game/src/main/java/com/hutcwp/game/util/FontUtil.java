package com.hutcwp.game.util;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.hutcwp.game.R;

import androidx.core.content.res.ResourcesCompat;

/**
 * Created by hutcwp on 2019-06-24 21:06
 *
 *
 **/
public class FontUtil {

    public static void setFont(TextView view, int fontRes) {
        if (view != null) {
            Typeface typeface = ResourcesCompat.getFont(view.getContext(), fontRes);
            view.setTypeface(typeface);
        }
    }
}
