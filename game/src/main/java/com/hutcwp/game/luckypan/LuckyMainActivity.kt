package com.hutcwp.game.luckypan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.game.R
import me.hutcwp.constant.Constants

@Route(path = Constants.RoutePath.LUCKY_MAIN_PAGE)
class LuckyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_main)
    }
}
