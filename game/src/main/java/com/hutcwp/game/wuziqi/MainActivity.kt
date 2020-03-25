package com.hutcwp.game.wuziqi

import android.annotation.SuppressLint
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
    private var curTVPlayer: TextView? = null
    private var tvPlayer1: TextView? = null
    private var tvPlayer2: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game_activity_main_wuziqi)
        initView()
        initData()
//        val ai = AI2Player()
//        ai.testMark()
    }

    private fun initData() {
        gameView?.let {
            gameManager = GameManager(it, this)
        }
    }

    private fun initView() {
        curTVPlayer = findViewById(R.id.tvPlayer)
        tvPlayer1 = findViewById(R.id.tvPlayer1)
        tvPlayer2 = findViewById(R.id.tvPlayer2)
        gameView = findViewById(R.id.gameView)
        btnStartGame = findViewById(R.id.btn_startGame)

        btnStartGame?.setOnClickListener {
            gameView?.resetGame()
            gameManager?.resetGame()
        }
    }

    fun updateCurPlayer(player: IGamePlayer) {
        val content = "当前回合：${player.name()}"
        curTVPlayer?.text = content
    }

    @SuppressLint("SetTextI18n")
    fun updatePlayerInfo(player1: IGamePlayer, player2: IGamePlayer) {
        tvPlayer1?.setTextColor(player1.pointColor())
        tvPlayer1?.text = player1.name()

        tvPlayer2?.setTextColor(player2.pointColor())
        tvPlayer2?.text = player2.name()
    }
}
