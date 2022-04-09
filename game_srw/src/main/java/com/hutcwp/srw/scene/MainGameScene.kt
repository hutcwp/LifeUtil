package com.hutcwp.srw.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hutcwp.srw.MainGameActivity
import com.hutcwp.srw.R
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 1:53 PM
 *  description : 游戏主场景，带地图
 */
class MainGameScene : BaseScene(R.layout.layout_scene_main_game), IScene {

    var map: MapView? = null
    var gameController: GameController? = null


    override fun firstInitView(rootView: View) {
        map = rootView.findViewById(R.id.game_map)
        map?.activity = activity
        gameController = GameController(activity as MainGameActivity, map!!)
    }

    override fun initData() {
        (activity as ISceneSwitch).setGameController(gameController!!)
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}