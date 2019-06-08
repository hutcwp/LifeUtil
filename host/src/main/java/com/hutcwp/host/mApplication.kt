package com.hutcwp.host

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.facebook.stetho.Stetho
import com.hutcwp.homepage.HomePageInitLogic
import me.hutcwp.BaseConfig
import me.hutcwp.app.BaseApplication
import me.hutcwp.cartoon.app.CartoonInitLogic
import me.hutcwp.webp.WebpBytebufferDecoder
import me.hutcwp.webp.WebpDrawable
import me.hutcwp.webp.WebpResourceDecoder
import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Created by hutcwp on 2019-06-02 02:43
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class mApplication : BaseApplication() {

    override fun initLogic() {
//        registerAppLogic(HomePageInitLogic())
        registerAppLogicClass(HomePageInitLogic::class)
        registerAppLogicClass(CartoonInitLogic::class)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        BaseConfig.setApplicationContext(applicationContext)
        Stetho.initializeWithDefaults(this);
        webpInit()
        arouterInit()
    }


    private fun arouterInit() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    private fun isDebug(): Boolean {
        return true
    }

    private fun webpInit() {
        val decoder = WebpResourceDecoder(this)
        val byteDecoder = WebpBytebufferDecoder(this)
        // use prepend() avoid intercept by default decoder
        Glide.get(this).registry
                .prepend<InputStream, WebpDrawable>(InputStream::class.java, WebpDrawable::class.java, decoder)
                .prepend(ByteBuffer::class.java, WebpDrawable::class.java, byteDecoder)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var context: Context? = null
            private set
    }
}
