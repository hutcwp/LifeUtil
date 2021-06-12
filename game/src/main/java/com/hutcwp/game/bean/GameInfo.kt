package com.hutcwp.game.bean

import com.hutcwp.game.core.GameManager

/**
 *
 * Created by hutcwp on 2019-06-23 18:49
 *
 *
 *
 **/
class GameInfo() {

    var gameStatus: GameManager.GameStatus = GameManager.GameStatus.UnKnow // 100代表游戏开始，101表示为开始
    var time: Int = 0 //代表已经过了几个回合


    override fun toString(): String {
        return "GameInfo(gameStatus=$gameStatus, time=$time)"
    }
}