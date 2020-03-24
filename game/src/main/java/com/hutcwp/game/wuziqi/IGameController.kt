package com.hutcwp.game.wuziqi

import android.graphics.Point
import com.hutcwp.game.wuziqi.player.IGamePlayer

/**
 *
 * Created by hutcwp on 2020-03-21 21:21
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IGameController {
    fun addNewPoint(point: Point, player: IGamePlayer): Boolean
}