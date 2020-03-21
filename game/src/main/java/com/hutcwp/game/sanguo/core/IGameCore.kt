package com.hutcwp.game.sanguo.core

import com.hutcwp.game.sanguo.bean.GameInfo
import com.hutcwp.game.sanguo.bean.Player

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