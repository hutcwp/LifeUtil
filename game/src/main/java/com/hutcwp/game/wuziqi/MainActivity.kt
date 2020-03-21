package com.hutcwp.game.wuziqi

import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import me.hutcwp.constant.Constants
import com.hutcwp.game.R

/**
 * 五子棋MainActivity
 */
@Route(path = Constants.RoutePath.WUZIQI_MAIN_PAGE)
class MainActivity : AppCompatActivity() {

    private var btnStartGame: Button? = null
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game_activity_main_wuziqi)
        initView()
    }

    private fun initView() {
        gameView = findViewById(R.id.gameView)
        btnStartGame = findViewById(R.id.btn_startGame)

        btnStartGame?.setOnClickListener {
            gameView?.resetGame()
        }
    }
}
