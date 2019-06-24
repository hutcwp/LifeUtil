package com.hutcwp.game.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hutcwp.game.event.GameEndEvent
import com.hutcwp.game.event.GameStartEvent
import me.hutcwp.event.IEventCompat
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 *
 * Created by hutcwp on 2019-06-24 20:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
open class BaseComponent : Fragment(), IEventCompat {

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: GameStartEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: GameEndEvent) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEventBind()
    }


    override fun onDestroy() {
        super.onDestroy()
        onUnEventBind()
    }
}