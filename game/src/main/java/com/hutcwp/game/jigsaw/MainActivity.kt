package com.hutcwp.game.jigsaw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.game.R
import com.hutcwp.game.jigsaw.view.JigsawLayout
import me.hutcwp.constant.Constants
import me.hutcwp.log.MLog

@Route(path = Constants.RoutePath.JIGSAW_MAIN_PAGE)
class MainActivity : AppCompatActivity() {

    private var jigsawLayout: JigsawLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity_main_jigsaw)
        initView()
        initData()
    }

    private fun initView() {
        jigsawLayout = findViewById(R.id.jigsawLayout)
        jigsawLayout?.setOnClickListener {
            initData()
        }
    }

    private fun initData() {
        val isGameOver = jigsawLayout?.isGameOver()
        MLog.info(TAG, "isGameOver=$isGameOver")
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
