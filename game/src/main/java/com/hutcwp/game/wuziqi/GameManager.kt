package com.hutcwp.game.wuziqi

import android.graphics.Point
import com.hutcwp.game.wuziqi.player.AIPlayer2
import com.hutcwp.game.wuziqi.player.AIPlayer3
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

    //    private val userPlayer = AIPlayer(this)
//    private val userPlayer = UserPlayer(this)
    private val userPlayer = AIPlayer2(this)
    private val aiPlayer = AIPlayer3(this)
    private var currentPlayer: IGamePlayer = aiPlayer
    private val aiPoints = mutableListOf<Point>()
    private val userPoints = mutableListOf<Point>()
    private val allFreePoints = mutableListOf<Point>()

    init {
        initGame()
        initData()
    }

    private fun initData() {
        autoPlay()
    }

    private fun initGame() {
        aiPoints.clear()
        userPoints.clear()
        allFreePoints.clear()
//        aiPlayer.initChessBoard()
        activity.updatePlayerInfo(userPlayer, aiPlayer)
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

    //更换用户回合
    private fun changePlayerRound() {
        currentPlayer = if (currentPlayer == userPlayer) {
            aiPlayer
        } else {
            userPlayer
        }
        autoPlay()
    }

    private fun autoPlay() {
        currentPlayer.let { player ->
            activity.updateCurPlayer(player)
            if (currentPlayer == userPlayer) {
                player.startPlay(userPoints, aiPoints, allFreePoints)
            } else {
                player.startPlay(aiPoints, userPoints, allFreePoints)
            }
        }
    }

    fun getCurrentUser(): IGamePlayer {
        return currentPlayer
    }

    fun canAddNewPoint(point: Point): Boolean {
        return gameView.canAddNewPoint(point.x, point.y)
    }

    override fun addNewPoint(point: Point, player: IGamePlayer): Boolean {
        if (!canAddNewPoint(point)) {
            MLog.error(TAG, "addNewPoint: can not add point($point)")
            return false
        }

        allFreePoints.remove(point)
        if (currentPlayer == userPlayer) {
            userPoints.add(point)
            MLog.info(TAG, "userPoints add point${point}")
        } else {
            aiPoints.add(point)
            MLog.info(TAG, "aiPoints add point${point}")
        }

        val addSuccess = gameView.addNewPoint(point, player)
        if (addSuccess && !checkGameFinished(player)) {
            changePlayerRound()
        }

        return addSuccess
    }

    private fun checkGameFinished(player: IGamePlayer): Boolean {
        var isGameOver = gameView.isGameOver()
        if (isGameOver) {
            SingleToastUtil.showToast("游戏结束,${player.name()}胜利")
        }

        if (allFreePoints.size == 0) {
            SingleToastUtil.showToast("平局")
            MLog.info(TAG, "平局")
            isGameOver = true
        }

        return isGameOver
    }

    fun resetGame() {
        initGame()
        changePlayerRound()
    }


    companion object {
        const val TAG = "GameManager"
    }
}