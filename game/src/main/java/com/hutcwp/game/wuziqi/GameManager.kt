package com.hutcwp.game.wuziqi

import android.graphics.Point
import com.hutcwp.game.wuziqi.player.AI2Player
import com.hutcwp.game.wuziqi.player.AIPlayer
import com.hutcwp.game.wuziqi.player.IGamePlayer
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil

/**
 *
 * Created by hutcwp on 2020-03-21 21:20
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameManager(private var gameView: GameView, private var activity: MainActivity) : IGameController {

    //    private val userPlayer = UserPlayer(this)
    private val userPlayer = AIPlayer(this)
    //    private val aiPlayer = AIPlayer()
    private val aiPlayer = AI2Player(this)
    private var currentPlayer: IGamePlayer = userPlayer
    private val aiPoints = mutableListOf<Point>()
    private val userPoints = mutableListOf<Point>()
    private val allFreePoints = mutableListOf<Point>()

    init {
        initGame()
        initData()
    }

    private fun initData() {
        changePlayer()
    }

    private fun initGame() {
        aiPoints.clear()
        userPoints.clear()
        allFreePoints.clear()
        aiPlayer.initChessBoard()
        currentPlayer = userPlayer
        for (i in 1..gameView.getBoardCount()) {
            for (j in 1..gameView.getBoardCount()) {
                allFreePoints.add(Point(i, j))
            }
        }

        gameView.setSelectPointListener(object : GameView.OnSelectPointListener {
            override fun selectPoint(x: Int, y: Int) {
//                userPlayer.selectPoint(x, y)
            }
        })
    }

    private fun changePlayer() {
        currentPlayer = if (currentPlayer == userPlayer) {
            aiPlayer
        } else {
            userPlayer
        }

        currentPlayer.let { player ->
            activity.updateCurPlayer(player)
            player.startPlay(userPoints, aiPoints, allFreePoints) { point ->
                addNewPoint(point, aiPlayer)
            }
        }
    }

    fun getCurrentUser(): IGamePlayer {
        return currentPlayer
    }

    fun canAddNewPoint(x: Int, y: Int): Boolean {
        return gameView.canAddNewPoint(x, y)
    }

    override fun addNewPoint(point: Point, player: IGamePlayer): Boolean {
        allFreePoints.remove(point)
        if (currentPlayer == userPlayer) {
            userPoints.add(point)
            MLog.info("GameManager", "userPoints add point${point}")
        } else {
            aiPoints.add(point)
            MLog.info("GameManager", "aiPoints add point${point}")
        }
        val addSuccess = gameView.addNewPoint(point, player)
        val isGameOver = gameView.isGameOver()
        if (addSuccess && !isGameOver) {
            changePlayer()
        }

        if (gameView.isGameOver()) {
            SingleToastUtil.showToast("游戏结束")
        }
        return addSuccess
    }

    fun resetGame() {
        initGame()
    }


    companion object {
        const val TAG = "GameManager"
    }
}