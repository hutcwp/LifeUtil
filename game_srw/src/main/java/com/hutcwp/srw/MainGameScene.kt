package com.hutcwp.srw

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.music.BackgroundMusic
import com.hutcwp.srw.view.MapView
import me.hutcwp.BaseConfig

/**
 *  author : kevin
 *  date : 2022/3/13 1:53 PM
 *  description :
 */
class MainGameScene : Fragment() {

    var map: MapView? = null
    var gameController: GameController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.layout_scene_main_game, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView(view)
        initData()
    }

    private fun initMapView(rootView: View) {
        map = rootView.findViewById<MapView>(R.id.game_map)
        map?.activity = activity
    }

    private fun initData() {
        gameController = GameController(activity as MainGameActivity, map!!)
        initDataCGameController()
    }

    fun initDataCGameController() {
        gameController ?: return
        (activity as MainGameActivity)?.setGameController(gameController!!)
    }






    companion object {
        const val TAG = "MainGameScene"
    }

}