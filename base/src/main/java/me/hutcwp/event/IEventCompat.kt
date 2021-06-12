package me.hutcwp.event

import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by hutcwp on 2019-06-24 19:40
 *
 *
 *
 **/
interface IEventCompat {
    fun onEventBind(){
        EventBus.getDefault().register(this);
    }
    fun onUnEventBind(){
        EventBus.getDefault().unregister(this);
    }
}