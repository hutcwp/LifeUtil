package com.hutcwp.game.core

import com.hutcwp.game.event.GameEndEvent
import com.hutcwp.game.event.GameStartEvent
import me.hutcwp.log.MLog
import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by hutcwp on 2019-06-23 18:48
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 游戏主程序
 **/
class GameManager private constructor() {


    private var mGameStatus = GameStatus.UnKnow


    fun onGameStart() {
        MLog.info(TAG, "mGameStatus = $mGameStatus")
        if (mGameStatus != GameStatus.Start) {
            updataGameStatus(GameStatus.Start)
            EventBus.getDefault().post(GameStartEvent())
        } else {
            //
        }
    }

    fun onGameOver() {
        MLog.info(TAG, "mGameStatus = $mGameStatus")
        if (mGameStatus != GameStatus.End) {
            updataGameStatus(GameStatus.End)
            EventBus.getDefault().post(GameEndEvent())
        }

    }

    private fun updataGameStatus(status: GameStatus) {
        MLog.info(TAG, "updata game status ,$mGameStatus to $status")
        mGameStatus = status
    }


    /**
     * 游戏状态
     */
    enum class GameStatus {
        UnKnow,
        Start,
        Pause,
        End,
    }


    private object Holder {
        val singleton = GameManager()
    }

    companion object {
        private const val TAG = "GameManager"
        fun getInstance(): GameManager {
            return Holder.singleton
        }
    }
}