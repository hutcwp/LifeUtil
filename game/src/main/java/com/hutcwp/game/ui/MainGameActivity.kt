package com.hutcwp.game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.game.R
import com.hutcwp.game.core.GameController
import com.hutcwp.game.ui.game.GameStatusComponent
import com.hutcwp.game.ui.game.MainGameComponent
import com.hutcwp.game.ui.game.MapComponent
import com.hutcwp.game.ui.welcome.WelcomeComponent
import com.hutcwp.game.util.PageManager

@Route(path = "/game/main")
class MainGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity_main_game)
        initView()
    }

    private fun initView() {
        PageManager.INSTANCE.init(R.id.content)
        val welcomeComponent = WelcomeComponent.newInstance()
        val mainGameComponent = MainGameComponent.newInstance()
        PageManager.INSTANCE.registerPage(PageManager.PAGE.WELCOME, PageManager.FragmentInfo(welcomeComponent))
        PageManager.INSTANCE.registerPage(PageManager.PAGE.GAME, PageManager.FragmentInfo(mainGameComponent))
        // 加载欢迎页面
        PageManager.INSTANCE.toPage(PageManager.PAGE.WELCOME, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PageManager.INSTANCE.unInit()
        GameController.finishGame()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
