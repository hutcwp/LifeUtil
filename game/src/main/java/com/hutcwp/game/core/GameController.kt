package com.hutcwp.game.core

/**
 *
 * Created by hutcwp on 2019-06-25 14:26
 *
 * 
 * 游戏控制器,客户端控制
 **/
class GameController {


    companion object {

        fun startGame() {
            GameManager.getInstance().onGameStart()
        }

        fun finishGame() {
            GameManager.getInstance().onGameOver()
        }

    }

}