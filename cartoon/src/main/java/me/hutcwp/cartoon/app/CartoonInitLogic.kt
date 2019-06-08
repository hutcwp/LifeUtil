package me.hutcwp.cartoon.app

import me.hutcwp.app.BaseAppLogic
import me.hutcwp.log.MLog

/**
 *
 * Created by hutcwp on 2019-06-08 17:17
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class CartoonInitLogic : BaseAppLogic() {

    override fun onCreate() {
        super.onCreate()
        MLog.info(TAG, "onCreate")
    }

    companion object {
        const val TAG = "CartoonInitLogic"
    }
}