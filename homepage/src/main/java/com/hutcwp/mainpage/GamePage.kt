package com.hutcwp.mainpage

import me.hutcwp.auto.IMainPage

/**
 *
 * Created by hutcwp on 2019-06-23 18:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GamePage : IMainPage {
    override fun getName(): String {
        return "游戏"
    }

    override fun getPath(): String {
        return "/game/srw"
    }
}