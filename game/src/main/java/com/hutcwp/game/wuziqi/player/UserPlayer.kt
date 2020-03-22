package com.hutcwp.game.wuziqi.player

import android.graphics.Point
import com.hutcwp.game.wuziqi.GamePoint

/**
 *
 * Created by hutcwp on 2020-03-21 21:42
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 玩家
 **/
class UserPlayer : IGamePlayer {
    override fun type(): Int {
        return 0
    }

    override fun name(): String {
        return "用户"
    }

    override fun makeNewPoint(paint: GamePoint) {

    }

    override fun startPlay(userPoints: MutableList<Point>, aiPoints: MutableList<Point>, allFreePoints: MutableList<Point>, block: (Point) -> Unit) {

    }

}