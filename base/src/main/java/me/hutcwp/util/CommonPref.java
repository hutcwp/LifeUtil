package me.hutcwp.util;

import android.content.Context;
import android.content.SharedPreferences;

import me.hutcwp.BasicConfig;

/**
 * Created by hutcwp on 2019-12-10 21:01
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class CommonPref extends SharedPref {
    public static final String COMMONREF_NAME = "CommonPref";
    private static final int OVER_LENGTH_STRING_VALUE = 300;

    private volatile static CommonPref sInst;


    private CommonPref(SharedPreferences preferences) {
        super(preferences);
    }

    public static CommonPref instance() {
        if (sInst == null) {
            synchronized (CommonPref.class) {
                if (sInst == null) {
                    SharedPreferences pref = SharedPreferencesUtils
                            .getSharedPreferences(BasicConfig.getApplicationContext(), COMMONREF_NAME,
                                    Context.MODE_PRIVATE);
                    sInst = new CommonPref(pref);
                }
            }
        }
        return sInst;
    }

}