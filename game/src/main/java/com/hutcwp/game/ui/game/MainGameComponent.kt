package com.hutcwp.game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hutcwp.game.R
import com.hutcwp.game.ui.BaseComponent

/**
 *
 * Created by hutcwp on 2019-06-25 14:52
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class MainGameComponent : BaseComponent() {

    var components: MutableList<BaseComponent>? = mutableListOf()

    companion object {
        fun newInstance(): MainGameComponent {
            return MainGameComponent()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_layout_main_game, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gameStatusComponent = GameStatusComponent.newInstance()
        val mapComponent = MapComponent.newInstance()
        val gameControllerComponent = GameControllerComponent.newInstance()
        gameStatusComponent.setRoot(this)
        mapComponent.setRoot(this)
        gameControllerComponent.setRoot(this)
        components?.add(gameStatusComponent)
        components?.add(mapComponent)
        components?.add(gameControllerComponent)
        childFragmentManager.beginTransaction()
                .replace(R.id.game_status, gameStatusComponent)
                .replace(R.id.game_main_map, mapComponent)
                .replace(R.id.game_controller, gameControllerComponent)
                .commitAllowingStateLoss()
    }

}