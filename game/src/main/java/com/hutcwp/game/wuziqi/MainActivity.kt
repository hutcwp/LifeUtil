package com.hutcwp.game.wuziqi

import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.game.R
import com.hutcwp.game.wuziqi.player.IGamePlayer
import me.hutcwp.constant.Constants

/**
 * 五子棋MainActivity
 */
@Route(path = Constants.RoutePath.WUZIQI_MAIN_PAGE)
class MainActivity : AppCompatActivity() {

    private var btnStartGame: Button? = null
    private var gameView: GameView? = null
    private var gameManager: GameManager? = null
    private var tvPlayer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game_activity_main_wuziqi)
        initView()
        initData()
    }

    private fun initData() {
        gameManager = GameManager(gameView, this)
    }

    private fun initView() {
        tvPlayer = findViewById(R.id.tvPlayer)
        gameView = findViewById(R.id.gameView)
        btnStartGame = findViewById(R.id.btn_startGame)

        btnStartGame?.setOnClickListener {
            gameView?.resetGame()
            gameManager?.release()
        }
    }

    fun updateCurPlayer(player: IGamePlayer) {
        val content = "当前回合：${player.name()}"
        tvPlayer?.text = content
    }
}
