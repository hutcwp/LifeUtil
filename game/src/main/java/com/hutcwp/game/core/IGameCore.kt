package com.hutcwp.game.core

import com.hutcwp.game.bean.GameInfo
import com.hutcwp.game.bean.Player

/**
 *
 * Created by hutcwp on 2019-06-25 16:40
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IGameCore {

    fun getPlayer(): Player?
    fun getGameInfo(): GameInfo?

}