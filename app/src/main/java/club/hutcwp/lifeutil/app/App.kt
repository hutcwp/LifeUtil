package club.hutcwp.lifeutil.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by liyu on 2016/11/2.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var context: Context? = null
            private set
    }

}
