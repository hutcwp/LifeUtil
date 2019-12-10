package com.hutcwp.homepage.assist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.homepage.R


/**
 * 可复制账户信息界面
 */
@Route(path = "/homepage/assist")
class AssistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_assist)

    }
}
