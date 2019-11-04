package com.hutcwp.blog

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path = "/blog/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blog_activity_main)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            ARouter.getInstance().build("/host/schema")
                    .withString("url", "https://github.com/hutcwp/BlogNote/")
                    .navigation()
        }
    }
}
