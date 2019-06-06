package com.hutcwp.host

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import me.hutcwp.log.MLog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MLog.debug(TAG, "MainActivity:onCreate")
        MLog.warn(TAG, "MainActivity:onCreate")
        MLog.info(TAG, "MainActivity:onCreate")
        MLog.debug(TAG, "MainActivity: s = %s", TAG)


        Handler().postDelayed({
            ARouter.getInstance().build("/homepage/home").navigation()
            finish()
        }, 500)
    }


    fun click(view: View) {
        ARouter.getInstance().build("/homepage/home").navigation()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}


