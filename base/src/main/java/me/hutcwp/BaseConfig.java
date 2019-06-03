package me.hutcwp;

import android.app.Application;
import android.content.Context;

/**
 * Created by hutcwp on 2019-06-02 15:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class BaseConfig {

    private static Context ApplicationContext = null;

    public static Context getApplicationContext() {
        return ApplicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        ApplicationContext = applicationContext;
    }
}
