package com.hutcwp.game.sanguo.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import me.hutcwp.constant.Constants
import com.hutcwp.game.R
import com.hutcwp.game.sanguo.core.GameController
import com.hutcwp.game.sanguo.game.MainGameComponent
import com.hutcwp.game.sanguo.welcome.WelcomeComponent
import com.hutcwp.game.sanguo.util.PageManager

@Route(path = Constants.RoutePath.SANGUO_MAIN_PAGE)
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
        showExitDialog()
    }

    private fun showExitDialog() {
        val bulider = AlertDialog.Builder(this)
//        bulider.setIcon()//在title的左边显示一个图片
        bulider.setTitle("提示")
        bulider.setMessage("确定要退出游戏吗？")
        bulider.setPositiveButton("确定") { dialog, arg1 ->
            dialog.dismiss()
            this@MainGameActivity.finish()
        }
        bulider.setNegativeButton("取消") { dialog, arg1 ->
            dialog.dismiss()
        }
        bulider.create().show()
    }
}
