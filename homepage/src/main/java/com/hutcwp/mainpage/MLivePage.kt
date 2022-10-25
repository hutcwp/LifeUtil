package com.hutcwp.mainpage

import me.hutcwp.auto.IMainPage
import me.hutcwp.constant.Constants

/**
 *
 * Created by hutcwp on 2019-06-23 18:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class MLivePage : IMainPage {
    override fun getName(): String {
        return "开播"
    }

    override fun getPath(): String {
        return Constants.RoutePath.MLIVE_CAMERA2
    }
}