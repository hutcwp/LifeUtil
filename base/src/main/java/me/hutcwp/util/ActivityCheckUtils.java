package me.hutcwp.util;

import android.app.Activity;
import android.os.Build;

/**
 * Created by hutcwp on 2019-06-25 15:13
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class ActivityCheckUtils {

    public static boolean checkActivityValid(Activity activity) {
        if (activity == null) {
            //YLog.w(this, "Fragment " + this + " not attached to Activity");
            return false;
        }

        if (activity.isFinishing()) {
            //YLog.w(this, "activity is finishing");
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !activity.isDestroyed();
        }

        return true;
    }
}