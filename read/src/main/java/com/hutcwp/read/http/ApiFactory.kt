package com.hutcwp.read.http

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

object ApiFactory {

    //用于同步处理
    private val monitor = Any()

    private var girlsController: GirlsController? = null

    fun getGirlsController(): GirlsController {
        if (girlsController == null) {
            synchronized(monitor) {
                girlsController = RetrofitManager.getInstance().create(GirlsController::class.java)
            }
        }
        return girlsController!!
    }
}
