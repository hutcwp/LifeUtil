package com.hutcwp.game.sanguo.core

/**
 *
 * Created by hutcwp on 2019-06-25 14:26
 * email: caiwenpeng@yy.com
 * YY: 909076244
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