package com.hutcwp.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.hutcwp.constant.Constants

@Route(path = Constants.RoutePath.GAME_MAIN_PAGE)
class GameMainActivity : AppCompatActivity() {

    private var rvGame: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)

        initView()
    }

    private fun initView() {
        rvGame = findViewById(R.id.rvGame)
        rvGame?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val data = mutableListOf<GameBean>()
        data.add(GameBean("三国志",Constants.RoutePath.SANGUO_MAIN_PAGE))
        data.add(GameBean("五子棋",Constants.RoutePath.WUZIQI_MAIN_PAGE))
        data.add(GameBean("幸运轮盘",Constants.RoutePath.LUCKY_MAIN_PAGE))
        val adapter = GameAdapter(data)
        rvGame?.adapter = adapter
    }

}
