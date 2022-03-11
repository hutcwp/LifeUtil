package com.hutcwp.srw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.view.MapView

@Route(path = "/game/srw")
class MainGameActivity : AppCompatActivity() {

    var map: MapView? = null
    var gameController: GameController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        initMapView()
        initData()
    }

    private fun initMapView() {
        map = findViewById(R.id.game_map)
        map?.activity = this
    }

    private fun initData() {
        gameController = GameController(map!!)
    }


    companion object {
        const val UNIT_MAP = 60
    }

}