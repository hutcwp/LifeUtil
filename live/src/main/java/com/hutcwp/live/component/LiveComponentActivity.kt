package com.hutcwp.live.component

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.live.R
import com.hutcwp.live.TestFragment
import me.hutcwp.constants.RoutePath

/**
 *  author : kevin
 *  date : 2021/12/12 2:08 AM
 *  description :
 */

@Route(path = RoutePath.LIVE_CHANNEL_COMPONENT, name = "直播间组件")
class LiveComponentActivity : com.hutcwp.component.ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_layout_live_component)

        initComponent()
        initTest()

    }

    private fun initTest() {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fl_test, TestFragment())
                ?.commitAllowingStateLoss()
    }

    private fun initComponent() {
        componentManager?.replace(R.id.fl_public_msg, findViewById(android.R.id.content), PublicMsgComponent())
    }


}