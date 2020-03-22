package com.hutcwp.game.wuziqi

import android.graphics.Color
import android.graphics.Point
import com.hutcwp.game.wuziqi.player.AIPlayer
import com.hutcwp.game.wuziqi.player.IGamePlayer
import com.hutcwp.game.wuziqi.player.UserPlayer
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil

/**
 *
 * Created by hutcwp on 2020-03-21 21:20
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameManager(private var gameView: GameView?, private var activity: MainActivity) : IGameController {

    private val userPlayer = UserPlayer()
    private val aiPlayer = AIPlayer()
    private var currentPlayer: IGamePlayer? = null
    private val aiPoints = mutableListOf<Point>()
    private val userPoints = mutableListOf<Point>()
    private val allFreePoints = mutableListOf<Point>()

    init {
        initGame()
    }

    private fun initGame() {
        aiPoints.clear()
        userPoints.clear()
        allFreePoints.clear()
        currentPlayer = userPlayer
        for (i in 1..14) {
            for (j in 1..14) {
                allFreePoints.add(Point(i, j))
            }
        }
        gameView?.let { gameView ->
            gameView.setSelectPointListener(object : GameView.OnSelectPointListener {
                override fun selectPoint(x: Float, y: Float) {
                    if (currentPlayer != userPlayer) {
                        SingleToastUtil.showToast("现在是${currentPlayer?.name()}回合，请稍等")
                        return
                    }

                    if (gameView.iFAddPoint(x, y)) {
                        addNewPoint(GamePoint(x, y, 0, Color.WHITE), userPlayer)
                        changePlayer()
                    } else {
                        SingleToastUtil.showToast("当前位置不能放置，请重新选择")
                    }
                }
            })
        }
    }

    override fun changePlayer() {
        currentPlayer = if (currentPlayer == userPlayer) {
            aiPlayer
        } else {
            userPlayer
        }

        currentPlayer?.let { player ->
            player.startPlay(userPoints, aiPoints, allFreePoints) { point ->
                val color = if (player == aiPlayer) {
                    Color.RED
                } else {
                    Color.WHITE
                }
                addNewPoint(GamePoint(point.x.toFloat(), point.y.toFloat(), player.type(), color), aiPlayer)
                changePlayer()
                activity.updateCurPlayer(player)
            }
        }
    }

    override fun addNewPoint(point: GamePoint, player: IGamePlayer) {
        val p = Point(point.x.toInt(), point.y.toInt())
        allFreePoints.remove(p)
        if (currentPlayer == userPlayer) {
            userPoints.add(p)
            MLog.info("GameManager", "userPoints add point${p}")
        } else {
            aiPoints.add(p)
            MLog.info("GameManager", "aiPoints add point${p}")
        }
        gameView?.addNewPoint(point, player)
    }

    fun release() {
        initGame()
    }

}