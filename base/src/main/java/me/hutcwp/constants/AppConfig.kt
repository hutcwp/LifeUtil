package me.hutcwp.constants

import android.app.Application
import android.os.Build

/**
 *  author : kevin
 *  date : 2020/11/27
 *  description :
 */
object AppConfig {

    private const val APP_FIRST_TIME_LAUNCH_KEY = "app_first_time_launch"


    var appId = 1 //应用标识
    var openId = "" //第三方登陆标识
    var openType = 0 //第三方登陆标识

    var appVersionCode = 0
    var appVersionName = "0"
    var osVersion = Build.VERSION.SDK // 系统版本号
    var mid = "" //设备id
    var channel = ""//发布渠道

    var manufacturer = Build.MANUFACTURER.toLowerCase() //设备制造商

//    private val isFirstStart by lazy { isFirstTimeLaunch() }

//
//    @kotlin.jvm.JvmField
//    var isLogRelease = false
    var app: Application? = null
//
//    fun isDebug(): Boolean {
//        return BuildConfig.CAN_DEBUGGABLE
//    }
//
//    fun isShowDevEnvSetting(): Boolean {
//        return BuildConfig.IS_SHOW_DEV_ENV_SETTING && isDebug()
//    }
//
//    /**
//     * 判断是否是首次启动ø
//     */
//    fun isFirstTimeStart(): Boolean {
//        return isFirstStart
//    }
//
//    private fun isFirstTimeLaunch(): Boolean {
//        val isFirstTimeLaunch = CommonSpUtil.mmkv?.decodeBool(APP_FIRST_TIME_LAUNCH_KEY, true)
//                ?: true
//        if (isFirstTimeLaunch) {
//            CommonSpUtil.mmkv?.encode(APP_FIRST_TIME_LAUNCH_KEY, false)
//        }
//        return isFirstTimeLaunch
//    }
//
//    /**
//     * 是否展示日志
//     */
//    fun isShowLog(): Boolean = BuildConfig.IS_SHOW_LOG
//
//    val env: Env
//        get() {
//            return if (isDebug()) {
//                val isOffice = CommonSpUtil.mmkv?.decodeBool("test_url_switch_flag", true)
//                        ?: true //默认正式环境
//                if (isOffice) Env.PRODUCT else Env.TEST
//            } else {
//                Env.PRODUCT
//            }
//        }
//
//    fun isTestEnv(): Boolean {
//        return env == Env.TEST
//    }
//
//    fun isSnapshotVersion() = appVersionName.toUpperCase(Locale.getDefault()).contains("SNAPSHOT")
//
//    /**
//     * 是否是发版包. 注意需要在 AppConfig 类初始化完成（相应的值设置完）后此方法才会返回正确的结果
//     */
//    fun isReleaseAPK() = !isDebug()
//            && !isShowDevEnvSetting()
//            && !isLogRelease
//            && !isSnapshotVersion()
}


enum class Env(val value: Int) {
    TEST(0), PRODUCT(1)
}

