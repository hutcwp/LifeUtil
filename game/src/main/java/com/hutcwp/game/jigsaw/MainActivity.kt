package com.hutcwp.game.jigsaw

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hutcwp.game.R
import com.hutcwp.game.jigsaw.view.JigsawLayout
import me.hutcwp.constant.Constants
import me.hutcwp.log.MLog

@Route(path = Constants.RoutePath.JIGSAW_MAIN_PAGE)
class MainActivity : AppCompatActivity() {

    private var jigsawLayout: JigsawLayout? = null
    private var fullImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity_main_jigsaw)
        initView()
        initData()

    }

    private fun initView() {
        fullImageView = findViewById(R.id.fullImageView)
        jigsawLayout = findViewById(R.id.jigsawLayout)
        jigsawLayout?.setOnClickListener {
            initData()
        }

        val url = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ1kIpNpzm1gnCQ0CdH3RxEfMS1csBr70GYOhqnDnZAf7xB0UDm"
        Glide.with(this).load(url).into(fullImageView!!)
    }

    private fun initData() {
        val isGameOver = jigsawLayout?.isGameOver()
        MLog.info(TAG, "isGameOver=$isGameOver")
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
