package com.hutcwp.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.game.R
import me.hutcwp.event.IEventCompat

/**
 *
 * Created by hutcwp on 2019-06-23 21:55
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameStatusComponent private constructor() : Fragment(), IEventCompat {

    companion object {
        fun Instance(): GameStatusComponent {
            return GameStatusComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_layout_game_status, container, false)
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