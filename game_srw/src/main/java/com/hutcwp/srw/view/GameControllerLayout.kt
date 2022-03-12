package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.controller.IGameController
import kotlinx.android.synthetic.main.layout_game_controller.view.*

/**
 *  author : kevin
 *  date : 2022/3/12 12:40 AM
 *  description : 游戏手柄控制视图
 */
class GameControllerLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var gameController: IGameController? = null

    init {
        View.inflate(context, R.layout.layout_game_controller, this)

        initView()
    }

    private fun initView() {
        btn_up?.setOnClickListener {
            gameController?.up()
        }
        btn_down?.setOnClickListener {
            gameController?.down()
        }
        btn_left?.setOnClickListener {
            gameController?.left()
        }
        btn_right?.setOnClickListener {
            gameController?.right()
        }
        btn_ok?.setOnClickListener {
            gameController?.ok()
        }
        btn_cancel?.setOnClickListener {
            gameController?.cancel()
        }
    }

}