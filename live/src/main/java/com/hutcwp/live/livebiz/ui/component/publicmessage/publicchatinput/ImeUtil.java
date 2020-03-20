package com.hutcwp.live.livebiz.ui.component.publicmessage.publicchatinput;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: xuduo
 * Date: 8/15/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 * this is a UIUtility class
 */
public class ImeUtil {

    public static void hideIME(Activity activity) {
        View view = activity.getCurrentFocus();
        if (null != view) {
            hideIME(activity, view);
        }
    }

    public static void hideIME(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @SuppressLint("PrivateApi")
    public static void showIME(Activity activity, View view) {
        if (null == view) {
            view = activity.getCurrentFocus();
            if (null == view) {
                return;
            }
        }
        InputMethodManager input = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (input == null) {
            return;
        }
        try {
            Field mNextServedView = input.getClass().getDeclaredField("mNextServedView");
            mNextServedView.setAccessible(true);
            mNextServedView.set(input, view);
        } catch (Throwable error) {
            Log.e("ImeUtil", error.getMessage());
        }
        input.showSoftInput(
                view, InputMethodManager.SHOW_FORCED);
    }

    public static void showIME(Activity activity, View view, int flag) {
        if (null == view) {
            view = activity.getCurrentFocus();
            if (null == view) {
                return;
            }
        }
        ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).showSoftInput(view, flag);
    }

//    public static void showIMEDelay(final Activity activity, final View view, long time) {
//        ScheduledTask.getInstance().scheduledDelayed(new Runnable() {
//            public void run() {
//                showIME(activity, view);
//            }
//        }, time);
//    }
}
