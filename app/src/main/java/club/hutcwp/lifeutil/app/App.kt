package club.hutcwp.lifeutil.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import me.hutcwp.webp.WebpBytebufferDecoder
import me.hutcwp.webp.WebpDrawable
import me.hutcwp.webp.WebpResourceDecoder
import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Created by liyu on 2016/11/2.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
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
        return false
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
