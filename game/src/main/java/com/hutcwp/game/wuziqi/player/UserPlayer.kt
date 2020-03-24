package com.hutcwp.game.wuziqi.player

import android.graphics.Color
import android.graphics.Point
import com.hutcwp.game.wuziqi.GameManager
import com.hutcwp.game.wuziqi.GamePoint
import com.hutcwp.game.wuziqi.GameView
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil

/**
 *
 * Created by hutcwp on 2020-03-21 21:42
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 玩家
 **/
class UserPlayer(private val manager: GameManager) : IGamePlayer, GameView.OnSelectPointListener {

    override fun pointColor(): Int {
        return Color.WHITE
    }

    override fun type(): Int {
        return -1
    }

    override fun name(): String {
        return "用户"
    }

    override fun makeNewPoint(paint: GamePoint) {

    }

    override fun startPlay(userPoints: MutableList<Point>, aiPoints: MutableList<Point>, allFreePoints: MutableList<Point>, block: (Point) -> Unit) {

    }

    override fun selectPoint(x: Int, y: Int) {
        if (manager.getCurrentUser() != this) {
//                    SingleToastUtil.showToast("现在是${currentPlayer?.name()}回合，请稍等")
            MLog.debug(TAG, "current is ai play...")
            return
        }

        if (manager.canAddNewPoint(x, y)) {
            manager.addNewPoint(Point(x, y), this)
        } else {
            SingleToastUtil.showToast("当前位置不能放置，请重新选择")
        }
    }

    companion object {
        const val TAG = "UserPlayer"
    }

}