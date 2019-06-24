package com.hutcwp.game

import com.hutcwp.game.event.GameEndEvent
import com.hutcwp.game.event.GameStartEvent
import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by hutcwp on 2019-06-23 18:48
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
object GameManager {

    private const val TAG = "GameManager"


    fun GameStart(){
        EventBus.getDefault().post(GameStartEvent())
    }

    fun GameOver() {
        EventBus.getDefault().post(GameEndEvent())

    }

}