package com.hutcwp.game.wuziqi.player

import android.graphics.Point
import com.hutcwp.game.wuziqi.GamePoint

/**
 *
 * Created by hutcwp on 2020-03-21 21:18
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/

interface IGamePlayer {

    fun pointColor(): Int

    fun type(): Int

    fun name(): String

    fun makeNewPoint(paint: GamePoint) //下棋子

    fun startPlay(myPoints: MutableList<Point>, enemyPoints: MutableList<Point>, allFreePoints: MutableList<Point>)

}