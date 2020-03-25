package com.hutcwp.game.wuziqi.player

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Point
import com.hutcwp.game.wuziqi.GameManager
import com.hutcwp.game.wuziqi.GamePoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 *
 * Created by hutcwp on 2020-03-25 14:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 基于赢法数组，两层思考
 **/
class AIPlayer3(private val manager: GameManager) : IGamePlayer {

    override fun pointColor(): Int {
        return Color.BLACK
    }

    override fun type(): Int {
        return 2
    }

    override fun name(): String {
        return "AIPlayer3"
    }

    override fun makeNewPoint(paint: GamePoint) {

    }

    @SuppressLint("CheckResult")
    override fun startPlay(myPoints: MutableList<Point>, enemyPoints: MutableList<Point>, allFreePoints: MutableList<Point>) {
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    initWin(myPoints, enemyPoints, allFreePoints)
                    val result = computerAI(allFreePoints)
                    result
                }.subscribe { result ->
                    MLog.info(TAG, "result=$result")
                    if (manager.getCurrentUser() != this) {
                        MLog.debug(TAG, "current is other user play...")
                    }

                    if (manager.canAddNewPoint(result)) {
                        manager.addNewPoint(Point(result.x, result.y), this)
                    } else {
                        SingleToastUtil.showToast("当前位置不能放置，请重新选择")
                        startPlay(myPoints, enemyPoints, allFreePoints)
                    }
                }
    }


    /*	AI难点解析
   赢法数组：记录了五子棋说有的赢法，三维数组
   每一种赢法的统计数组，一维数组
   如何判断胜负
   计算机落子规则*/

    var wins = Array(15) { Array(15) { BooleanArray(1500) } }
    var chessBoard = Array(15) { IntArray(15) }
    var count = 0

    var myWin = IntArray(count) //玩家赢法数组
    var enemyWin = IntArray(count) //电脑赢法数组


    init {
        initGame()
    }

    private fun initGame() {
        for (i in 0..14) {
            for (j in 0..14) {
                chessBoard[i][j] = 0
            }
        }

        //赢法数组
        for (i in 0..14) {
            for (j in 0..10) {
                for (k in 0..4) {
                    wins[i][j + k][count] = true
                }
                count++
            }
        }
        for (i in 0..14) {
            for (j in 0..10) {
                for (k in 0..4) {
                    wins[j + k][i][count] = true
                }
                count++
            }
        }
        for (i in 0..10) {
            for (j in 0..10) {
                for (k in 0..4) {
                    wins[i + k][j + k][count] = true
                }
                count++
            }
        }
        for (i in 0..10) {
            for (j in 14 downTo 4) {
                for (k in 0..4) {
                    wins[i + k][j - k][count] = true
                }
                count++
            }
        }

        myWin = IntArray(count) //玩家赢法数组
        enemyWin = IntArray(count) //电脑赢法数组
        //初始化赢法数组
        for (i in 0 until count) {
            myWin[i] = 0
            enemyWin[i] = 0
        }
    }

    private fun initWin(myPoints: MutableList<Point>, enemyPoints: MutableList<Point>, allFreePoints: MutableList<Point>) {
        myWin = IntArray(count)
        enemyWin = IntArray(count)
        myPoints.forEach {
            val i = it.x - 1
            val j = it.y - 1
            chessBoard[i][j] = 1 //自己的棋子标识为1

            //落下子后需要进行统计
            for (k in 0 until count) {
                if (wins[i][j][k]) { //某种赢的某子true
                    myWin[k]++ //离胜利又进一步
                    enemyWin[k] = 6 //该种赢法计算机没有机会了
                    if (myWin[k] == 5) {
                        MLog.info(TAG, "我赢了")
                    }
                }
            }
        }

        enemyPoints.forEach {
            val i = it.x - 1
            val j = it.y - 1
            chessBoard[i][j] = 2 //敌人的棋子标识为1

            //计算机落完子后需要进行统计
            for (k in 0 until count) {
                if (wins[i][j][k]) { //某种赢的某子true
                    enemyWin[k]++ //离胜利又进一步
                    myWin[k] = 6 //该种赢法计算机没有机会了
                    if (enemyWin[k] == 5) { //如果达到5就赢了
                        MLog.info(TAG, "enemy赢了")
                    }
                }
            }
        }
    }

    private fun computerAI(allFreePoints: MutableList<Point>): Point {
        val myScore = Array(15) { IntArray(15) }
        val computerScore = Array(15) { IntArray(15) }

        var iMax = 0
        var x = 0
        var y = 0

        for (i in 0..14) {
            for (j in 0..14) {
                if (chessBoard[i][j] == 0) {
                    for (k in 0 until count) {
                        if (wins[i][j][k]) {
                            if (myWin[k] == 1) {
                                myScore[i][j] += 200
                            } else if (myWin[k] == 2) {
                                myScore[i][j] += 400
                            } else if (myWin[k] == 3) {
                                myScore[i][j] += 2000
                            } else if (myWin[k] == 4) {
                                myScore[i][j] += 10000
                            }

                            if (enemyWin[k] == 1) {
                                computerScore[i][j] += 400
                            } else if (enemyWin[k] == 2) {
                                computerScore[i][j] += 800
                            } else if (enemyWin[k] == 3) {
                                computerScore[i][j] += 2200
                            } else if (enemyWin[k] == 4) {
                                computerScore[i][j] += 20000
                            }
                        }
                    }

                    if (myScore[i][j] > iMax) {
                        iMax = myScore[i][j]
                        x = i
                        y = j
                    } else if (myScore[i][j] == iMax) {
                        if (computerScore[i][j] > computerScore[x][y]) {
                            x = i
                            y = j
                        }
                    }

                    if (computerScore[i][j] > iMax) {
                        iMax = computerScore[i][j]
                        x = i
                        y = j
                    } else if (computerScore[i][j] == iMax) {
                        if (myScore[i][j] > myScore[x][y]) {
                            x = i
                            y = j
                        }
                    }
                }
            }
        }

        if (iMax == 0 && allFreePoints.size > 0) {
            val random = Random.nextInt(allFreePoints.size)
            val point = allFreePoints[random]
            MLog.info(TAG, "get from random p = $point")
            return point
        }

        MLog.info(TAG, "computerAI: x=${x + 1},y=${y + 1}")
        return Point(x + 1, y + 1)
    }


    companion object {
        const val TAG = "AIPlayer3"
    }

}
