package com.hutcwp.game.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hutcwp.game.R
import com.hutcwp.game.core.GameController
import com.hutcwp.game.event.GameEndEvent
import com.hutcwp.game.event.GameStartEvent
import com.hutcwp.game.ui.BaseComponent
import com.hutcwp.game.util.PageManager
import kotlinx.android.synthetic.main.game_layout_welcome.*
import me.hutcwp.log.MLog


/**
 *
 * Created by hutcwp on 2019-06-25 14:43
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class WelcomeComponent : BaseComponent() {


    companion object {
        private const val TAG = "WelcomeComponent"
        fun newInstance(): WelcomeComponent {
            return WelcomeComponent()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_layout_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStartGame.setOnClickListener {
            GameController.startGame()
        }
        btnFinishGame.setOnClickListener {
            GameController.finishGame()
        }
    }


    override fun onGameStartEvent(event: GameStartEvent) {
        MLog.info(TAG, "onGameStartEvent")
        PageManager.INSTANCE.toPage(PageManager.PAGE.GAME, activity)
    }

    override fun onGameEndEvent(event: GameEndEvent) {
        MLog.info(TAG, "onGameEndEvent")
    }
}