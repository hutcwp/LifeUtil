package me.hutcwp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;

import java.io.File;
import java.util.HashMap;

import me.hutcwp.BasicConfig;
import me.hutcwp.log.MLog;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hutcwp on 2019-12-10 21:03
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class SharedPreferencesUtils {
    private static String TAG = "SharedPreferencesUtils";
    private static final HashMap<String, SharedPreferences> sSharedPrefs = new HashMap<String, SharedPreferences>();

    private static volatile File sSharedPrefDir = null;
    private static volatile File sMMkvDir = null;

    private static SharedPreferences mDefaultPrf;
    private static final String DEFAULT_PRF_NAME = "default";

    private static File getSharedPrefsFile(Context context, String name) {
        if (sSharedPrefDir != null) {
            return new File(sSharedPrefDir, name + ".xml");
        }
        ApplicationInfo info = context.getApplicationContext() == null ?
                context.getApplicationInfo() : context.getApplicationContext().getApplicationInfo();
        File dir = new File(info.dataDir);
        MLog.info("SharedPreferencesUtils", dir.toString());
        File sharedPrefDir = new File(dir, "/shared_prefs");
        if (!sharedPrefDir.exists()) {
            if (sharedPrefDir.mkdir()) {
                sSharedPrefDir = sharedPrefDir;
            }
        } else {
            sSharedPrefDir = sharedPrefDir;
        }

        return new File(sharedPrefDir, name + ".xml");
    }

    public static SharedPreferences getDefaultPrf() {
        if (mDefaultPrf == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (mDefaultPrf == null) {
                    mDefaultPrf = getSharedPreferences(BasicConfig.getApplicationContext(), DEFAULT_PRF_NAME,
                            MODE_PRIVATE);
                }
            }
        }

        return mDefaultPrf;
    }

    private static File getMMKVFile(Context context, String name) {
        if (sMMkvDir != null) {
            return new File(sMMkvDir, name);
        }
        ApplicationInfo info = context.getApplicationContext() == null ?
                context.getApplicationInfo() : context.getApplicationContext().getApplicationInfo();
        File dir = new File(info.dataDir + "/files");
        MLog.info(TAG, "getMMKVFile:" + dir.toString());
        File mmkvDir = new File(dir, "mmkv");
        if (!mmkvDir.exists()) {
            if (mmkvDir.mkdir()) {
                sMMkvDir = mmkvDir;
            }
        } else {
            sMMkvDir = mmkvDir;
        }
        return new File(mmkvDir, name);
    }


    public static SharedPreferences getSharedPreferences(final Context context,
                                                         final String name, final int mode) {
        SharedPreferences sp;
        synchronized (sSharedPrefs) {
            sp = sSharedPrefs.get(name);
            if (sp == null) {
                File prefsFile = getSharedPrefsFile(context, name);
                sp = BasicConfig.getApplicationContext().getSharedPreferences(name, mode);
                sSharedPrefs.put(name, sp);
                return sp;
            }
        }
        MLog.debug("getSharedPreferences", "get:" + name + ",is_16:");
        return sp;
    }


}

