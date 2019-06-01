package club.hutcwp.lifeutil.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
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
