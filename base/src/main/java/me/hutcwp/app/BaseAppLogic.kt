package me.hutcwp.app

import android.content.res.Configuration

/**
 *
 * Created by hutcwp on 2019-06-08 16:09
 *
 *
 *
 **/
open class BaseAppLogic {
    protected lateinit var mApplication: BaseApplication

    open fun onCreate() {

    }

    open fun onTerminate() {

    }

    open fun onLowMemory() {

    }

    open fun onLowMemory(level: Int) {

    }

    open fun onConfigurationChanged(newConfig: Configuration) {

    }

}