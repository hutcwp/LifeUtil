package me.hutcwp.app

import android.app.Application

/**
 *
 * Created by hutcwp on 2019-06-08 16:13
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
abstract class BaseApplication : Application() {

    private val logicList: MutableList<BaseAppLogic> = mutableListOf()

    protected fun registerAppLogic(logic: BaseAppLogic) {
        // todo 判断唯一性
        logicList.add(logic)
    }

    override fun onCreate() {
        super.onCreate()
        initLogic()
        logicList.forEach {
            it.onCreate()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        logicList.forEach {
            it.onTerminate()
        }
    }

    abstract fun initLogic()


}