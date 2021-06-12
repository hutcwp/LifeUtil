package me.hutcwp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import java.util.Stack;

import me.hutcwp.log.MLog;

import static android.app.Activity.RESULT_OK;

/**
 * The type App manager.
 * app管理类
 */
public class AppManager {

    private static Stack<Activity> activityStack = new Stack<>();
    private volatile static AppManager instance = new AppManager();

    private AppManager() {
    }

    /**
     * 单一实例
     * Single Instance
     * @return the instance
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     * Add the Activity to the stack
     * @param activity the activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     * Gets the current Activity (the last pressed in the stack)
     * @return the activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     * End the current Activity (the last pressed in the stack)
     */
    public void finishActivity() {
        if(activityStack.empty()){
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    /**
     * 待结果回调的activity
     * Waiting to be called back activity
     * @param intent   the intent 意图
     * @param activity the activity
     * @param bundle   the bundle
     */
    public void finishActivityByCallBack(Intent intent, Activity activity, Bundle bundle) {
        intent.putExtras(bundle);
        activity.setResult(RESULT_OK, intent);
        AppManager.getInstance().finishActivity(activity);
    }

    /**
     * 移除指定的Activity
     * Remove the specified Activity
     * @param activity the activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }
    /**
     * 结束指定的Activity
     * End the specified Activity
     * @param activity the activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     * Ends the Activity that specifies the class name.
     * @param cls the cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     * End all Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public int activietySizes() {
        if (null != activityStack) {
            return activityStack.size();
        }
        return 0;
    }

    /**
     * 用于跳转
     * Used to jump
     * @param context the Context
     * @param cls      the cls 清屏
     * @param bundle   the bundle
     */
    public void jumpActivity(Context context, Class<? extends Activity> cls, Bundle bundle) {
        if(context == null ){
            return;
        }
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    /**
     * 用于跳转
     * Used to jump
     * @param activity the activity
     * @param cls      the cls 清屏
     * @param bundle   the bundle
     */
    public void jumpActivity(Activity activity, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public void jumpActivity(Activity activity, Class<? extends Activity> cls) {
        jumpActivity(activity,cls,null);
    }

    /**
     * 待结果回调的跳转
     * A jump to a callback pending results
     * @param activity   the activity
     * @param cls        the cls
     * @param bundle     the bundle
     * @param requestCode the requestCode
     */
    public void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 返回当前stack的大小
     * Returns the size of the current stack
     * @return the activity size
     */
    public int getActivitySize() {
        if (null != activityStack) {
            return activityStack.size();
        }
        return 0;
    }


    /**
     * 退出应用程序
     * exit application
     */
    private long exitTime = 0;

    /**
     * Safety exit app.
     * 安全退出应用程序
     * @param context the context
     */
    public void safetyExitApp(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(context.getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            try {
                finishAllActivity();
                /*
                * 杀死该应用进程
                * Kill the application process
                * */
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            } catch (Exception e) {
                MLog.error(e);
            }
        }
    }
}