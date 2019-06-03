package com.hutcwp.host

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        ARouter.getInstance().build("/cartoon/comic").navigation()
//        startActivity(Intent(this,HomeActivity::class.java))
        Handler().postDelayed({
//            ARouter.getInstance().build("/homepage/home").navigation()
            ARouter.getInstance().build("/cartoon/demo").navigation()
        }, 500)
    }


    fun click(view: View){
        ARouter.getInstance().build("/homepage/home").navigation()
    }
}


