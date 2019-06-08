package me.hutcwp.app

import android.app.Application
import me.hutcwp.log.MLog
import kotlin.reflect.KClass

/**
 *
 * Created by hutcwp on 2019-06-08 16:13
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
abstract class BaseApplication : Application() {

    private val logicList: MutableList<BaseAppLogic> = mutableListOf()

    private val logicClassSet: MutableSet<KClass<out BaseAppLogic>> = mutableSetOf()

    protected fun registerAppLogicClass(clazz: KClass<out BaseAppLogic>) {
        logicClassSet.add(clazz)
    }

    private fun registerAppLogic(logic: BaseAppLogic) {
        // todo 判断唯一性
        logicList.add(logic)
    }

    override fun onCreate() {
        super.onCreate()
        initLogic()
        initInstance()
        logicList.forEach {
            it.onCreate()
        }
    }

    private fun initInstance() {
        logicClassSet.forEach {
            try {
                val c = it.java
                //获取public的无参数构造器
                val origin = c.getDeclaredConstructor()
                //构造器是private的，所以这里要设置为可访问
                origin.isAccessible = true
                val appLogic = origin.newInstance()
                registerAppLogic(appLogic)
            } catch (e: Exception) {
                MLog.error("BaseApplication", "initInstance error ,e = ", e)
            }
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