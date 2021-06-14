package com.hutcwp.mainpage

import me.hutcwp.auto.IMainPage
import me.hutcwp.constants.RoutePath

/**
 *  author : kevin
 *  date : 2021/6/14
 *  description :
 */
class MusicPage : IMainPage {

    override fun getName(): String {
        return "音乐"
    }

    override fun getPath(): String {
        return RoutePath.MUSIC_MAIN
    }
}