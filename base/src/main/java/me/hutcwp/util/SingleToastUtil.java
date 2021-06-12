package me.hutcwp.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import me.hutcwp.BasicConfig;

/**
 * Created by hutcwp on 2019-12-10 17:58
 *
 *
 **/
public class SingleToastUtil {
    private static String oldMsg;
    private static long latestToastTime = 0;

    public static void showToast(String s) {
        showToast(BasicConfig.getApplicationContext(), s);
    }

    public static void showToast(Context context, String s) {
        showToast(context, s, 3000L);
    }

    public static void showToastCenter(String s) {
        showToastCenter(s, 3000L);
    }

    private static void setText(CharSequence charSequence) {
        //单独使用toast.setText，如果不能保证都在主线程操作，会有This Toast was not created with Toast.makeText()异常风险
        synchronized (SingleToastUtil.class) {
            if (charSequence instanceof String) {
                oldMsg = (String) charSequence;
            }
        }
    }

    public static void showToast(int resId) {
        Context context = BasicConfig.getApplicationContext();
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void replaceToast(Context context, String str) {
        if (oldMsg == null) {
            showToast(context, str);
        } else {
            oldMsg = str;
            setText(str);
        }
    }

    public static void showToast(Context context, String s, long timeIntervals) {
        if (oldMsg == null) {
            if (BasicConfig.getApplicationContext() == null) {
                return;
            }
            oldMsg = s;
            Toast.makeText(BasicConfig.getApplicationContext(), s, Toast.LENGTH_LONG).show();
            latestToastTime = System.currentTimeMillis();
        } else {
            if (s.equals(oldMsg)) {
                long twoTime = System.currentTimeMillis();
                if (twoTime - latestToastTime > timeIntervals) {
                    latestToastTime = twoTime;
                    Toast.makeText(BasicConfig.getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            } else {
                latestToastTime = System.currentTimeMillis();
                oldMsg = s;
                Toast.makeText(BasicConfig.getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void showToastCenter(String s, long timeIntervals) {
        if (oldMsg == null) {
            if (BasicConfig.getApplicationContext() == null) {
                return;
            }
            oldMsg = s;
            Toast toast = Toast.makeText(BasicConfig.getApplicationContext(), s, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            latestToastTime = System.currentTimeMillis();
        } else {
            if (s.equals(oldMsg)) {
                long twoTime = System.currentTimeMillis();
                if (twoTime - latestToastTime > timeIntervals) {
                    latestToastTime = twoTime;
                    Toast toast = Toast.makeText(BasicConfig.getApplicationContext(),
                            s, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                latestToastTime = System.currentTimeMillis();
                oldMsg = s;
                Toast toast = Toast.makeText(BasicConfig.getApplicationContext(), s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
