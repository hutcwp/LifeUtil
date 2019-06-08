package me.hutcwp.cartoon.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SpUtils {

    public static void putInt(String key, int value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sp", MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }
}
