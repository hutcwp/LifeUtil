package com.hutcwp.mainpage

import me.hutcwp.auto.IMainPage
import me.hutcwp.util.RoutePath

/**
 *  author : kevin
 *  date : 2021/5/9 6:58 PM
 *  description :
 */
class TiktokPage : IMainPage {

    override fun getName(): String {
        return "短视频"
    }

    override fun getPath(): String {
        return RoutePath.TIKTOK_MAIN
    }
}