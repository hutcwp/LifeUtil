package com.hutcwp.game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.game.R
import java.util.zip.Inflater

@Route(path = "/game/main")
class MainGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity_main_game)
        initView()
    }

    private fun initView() {
        val gameStatusComponent = GameStatusComponent.Instance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.game_status, gameStatusComponent)
                .commitAllowingStateLoss()
    }
}
