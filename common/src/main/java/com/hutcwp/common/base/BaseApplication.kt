package com.hutcwp.common.base

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import me.hutcwp.util.*

/**
 * The type Base application.
 * 应用基类
 */
abstract class BaseApplication : MultiDexApplication() {
    /**
     * 非0表示应用在前台，0则表示在后台
     * Non-zero means applied in the foreground, and zero means applied in the background
     */
    private var activityState = 0
    private var mBackgroundObservable: BackgroundObservable? = null

    override fun onCreate() {
        super.onCreate()
        /*
         * 主进程初始化数据
         * The main process initializes the data
         * */
        if (ProcessUtils.isMainProcess()) {
            Utils.init(this)
            registerLifecycleCallbacks()
            initApplication(true)
            mBackgroundObservable = BackgroundObservable()
        } else {
            initApplication(false)
        }
    }

    /**
     * Init application.
     * 初始化应用程序
     *
     * @param isMainProcess the is main process  主要过程
     */
    abstract fun initApplication(isMainProcess: Boolean)

    /**
     * 是否在后台
     * Is it in the background
     *
     * @return the boolean
     */
    val isBackground: Boolean get() = activityState == 0

    /**
     * 进入后台
     * Into the background
     */
    fun turnToBackground() {}

    /**
     * 进入前台
     * Into the front desk
     */
    fun turnToForeground() {}

    /**
     * activity生命周期监听
     * activity Life cycle monitoring
     */
    private fun registerLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppManager.getInstance().addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                ++activityState
                if (activityState == 1) {
                    mBackgroundObservable!!.turnToForeground()
                    /*
                     * 从后台切到前台
                     * Cut from the back to the front
                     * */
                    turnToForeground()
                }
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {
                --activityState
                if (activityState == 0) { //切换到后台
                    mBackgroundObservable!!.turnToBackground()
                    turnToBackground()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                AppManager.getInstance().removeActivity(activity)
            }
        })
    }

    /**
     * 注册app进入后台的监听
     * register background observer
     */
    fun registerBackgroundObserver(observer: BackgroundObserver?) {
        if (mBackgroundObservable != null) {
            mBackgroundObservable!!.registerObserver(observer)
        }
    }

    /**
     * 注销app进入后台的监听
     * unregister background observer
     */
    fun unregisterBackgroundObserver(observer: BackgroundObserver?) {
        if (mBackgroundObservable != null) {
            mBackgroundObservable!!.unregisterObserver(observer)
        }
    }


//    override fun getResources(): Resources {
//        //字体大小不随设置变动而改变。
//        val resources = super.getResources()
//        val newConfig = resources.configuration
//        val displayMetrics = resources.displayMetrics
//
//        if (newConfig.fontScale != 1F) {
//            newConfig.fontScale = 1F
//            // if (Build.VERSION.SDK_INT >= 17) {
//            //     Context configurationContext = createConfigurationContext(newConfig);
//            //    resources = configurationContext.getResources();
//            //   displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
//            // } else {
//            resources.updateConfiguration(newConfig, displayMetrics);
//            // }
//        }
//        return resources;
//    }
//
//    @Override
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig);
//        // 字体大小变更,重启App
//        if (newConfig.fontScale != 1F) {
//            AppUtils.exitApp()
//        }
//    }
}