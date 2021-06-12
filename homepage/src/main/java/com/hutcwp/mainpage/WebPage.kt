package com.hutcwp.mainpage

import me.hutcwp.auto.IMainPage
import me.hutcwp.constants.RoutePath

/**
 *  author :
 *  date : 2021/6/13
 *  description :
 */
class WebPage : IMainPage {

    override fun getName(): String {
        return "web页面"
    }

    override fun getPath(): String {
        return RoutePath.WEB_MAIN
    }
}