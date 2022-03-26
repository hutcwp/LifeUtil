package com.hutcwp.srw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 1:53 PM
 *  description : 游戏主场景，带地图
 */
class MainGameScene : Fragment() {

    var map: MapView? = null
    var gameController: GameController? = null
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.layout_scene_main_game, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataWithContext(true)
    }

    private fun initMapView(rootView: View) {
        map = rootView.findViewById(R.id.game_map)
        map?.activity = activity
    }

    /**
     * 这个方法回调是有context的
     */
    fun initDataWithContext(firstInit: Boolean) {
        if (firstInit) {
            initMapView(rootView!!)
            gameController = GameController(activity as MainGameActivity, map!!)
        }

        (activity as MainGameActivity).setGameController(gameController!!)
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}