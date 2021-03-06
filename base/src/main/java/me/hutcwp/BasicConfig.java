package me.hutcwp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

/**
 * Created by hutcwp on 2019-06-02 15:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class BasicConfig {

    private  BasicConfig mInstance = null;

    @SuppressLint("StaticFieldLeak")
    private static Activity mTopActivity = null;
    private static Context ApplicationContext = null;


    public static Context getApplicationContext() {
        return ApplicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        ApplicationContext = applicationContext;
    }

//    public static Activity getTopActivity() {
//        return mTopActivity;
//    }
//
//    public static Activity setTopActivity(Activity activity) {
//        return mTopActivity = activity;
//    }

    public BasicConfig getInstance() {
        if (mInstance == null) {
            mInstance = new BasicConfig();
        }
        return mInstance;
    }

}
