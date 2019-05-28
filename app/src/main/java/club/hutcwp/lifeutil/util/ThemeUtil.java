package club.hutcwp.lifeutil.util;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;
import android.util.TypedValue;

import club.hutcwp.lifeutil.R;

/**
 * 多主题切换工具
 * Created by liyu on 2016/9/28.
 */

public class ThemeUtil {

    public static int getThemeColor(Context context, int attrRes) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        int color = typedArray.getColor(0, 0xffffff);
        typedArray.recycle();
        return color;
    }

    public static int getCurrentColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.resourceId;
    }

    public static int getCurrentColorPrimaryDark(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.resourceId;
    }

    public static int getCurrentColorAccent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.resourceId;
    }

    public static Drawable setTintDrawable(Drawable drawable, Context context) {
        Drawable drawable1 = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(drawable1, context.getResources().getColorStateList(getCurrentColorPrimary(context)));
        return drawable1;
    }

    public static Drawable setTintDrawable(@DrawableRes int drawable, Context context, @ColorRes int color) {
        Drawable drawable1 = DrawableCompat.wrap(context.getResources().getDrawable(drawable));
        DrawableCompat.setTintList(drawable1, context.getResources().getColorStateList(color));
        return drawable1;
    }
}
