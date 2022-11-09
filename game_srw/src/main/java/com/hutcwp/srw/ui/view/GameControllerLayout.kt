package com.hutcwp.srw.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.controller.IGameController
import kotlinx.android.synthetic.main.layout_game_controller.view.*
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/12 12:40 AM
 *  description : 游戏手柄控制视图
 */
class GameControllerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var enable: Boolean = true //是否开启手柄控制

    private var gameListenerList: MutableList<IGameController> = mutableListOf()

    init {
        View.inflate(context, R.layout.layout_game_controller, this)
        initView()
    }

    private fun initView() {
        btn_up?.setOnClickListener(this)
        btn_down?.setOnClickListener(this)
        btn_left?.setOnClickListener(this)
        btn_right?.setOnClickListener(this)
        btn_ok?.setOnClickListener(this)
        btn_cancel?.setOnClickListener(this)
    }

    fun addListener(l: IGameController) {
        if (!gameListenerList.contains(l)) {
            gameListenerList.add(l)
        }
    }

    fun removeListener(l: IGameController) {
        if (gameListenerList.contains(l)) {
            gameListenerList.add(l)
        }
    }

    override fun onClick(view: View?) {
        view ?: return
        if (enable.not()) {
            MLog.warn(TAG, "手柄控制器开关未开!")
            return
        }
        if (gameListenerList.isEmpty()) {
            MLog.warn(TAG, "还没有设置手柄接收器【gameController=null】!")
            return
        }

        when (view.id) {
            R.id.btn_up -> {
                gameListenerList.forEach {
                    it.up()
                }
            }
            R.id.btn_down -> {
                gameListenerList.forEach {
                    it.down()
                }
            }
            R.id.btn_left -> {
                gameListenerList.forEach {
                    it.left()
                }
            }
            R.id.btn_right -> {
                gameListenerList.forEach {
                    it.right()
                }
            }
            R.id.btn_ok -> {
                gameListenerList.forEach {
                    it.ok()
                }
            }
            R.id.btn_cancel -> {
                gameListenerList.forEach {
                    it.cancel()
                }
            }
        }
    }

    override fun removeAllViews() {
        super.removeAllViews()
        gameListenerList.clear()
    }


    companion object {
        private const val TAG = "GameControllerLayout"
    }
}