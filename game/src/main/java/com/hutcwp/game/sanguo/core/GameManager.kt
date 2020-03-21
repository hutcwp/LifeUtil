package com.hutcwp.game.sanguo.core

import com.hutcwp.game.sanguo.bean.GameInfo
import com.hutcwp.game.sanguo.bean.Player
import com.hutcwp.game.sanguo.event.GameEndEvent
import com.hutcwp.game.sanguo.event.GameInfoChangeEvent
import com.hutcwp.game.sanguo.event.GameStartEvent
import com.hutcwp.game.sanguo.event.PlayerInfoChangeEvent
import me.hutcwp.log.MLog
import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by hutcwp on 2019-06-23 18:48
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 游戏主程序
 **/
class GameManager private constructor() : IGameCore {


    private var mCurPlayer: Player? = null
    private var mCurGameInfo: GameInfo? = null
    private var mGameStatus = GameStatus.UnKnow

    fun onGameStart() {
        MLog.info(TAG, "mGameStatus = $mGameStatus")
        if (mGameStatus != GameStatus.Start) {
            updataGameStatus(GameStatus.Start)
            initPlayerInfo()
            initGameInfo()
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

    private fun initGameInfo() {
        val gameInfo = GameInfo()
        gameInfo.time = 1
        gameInfo.gameStatus = mGameStatus
        updataGameInfo(gameInfo)
    }

    private fun initPlayerInfo() {
        val player = Player()
        player.nick = "昵称而已咯"
        player.hp = 50
        player.attack = 10
        player.defence = 5
        updataPlayerInfo(player)
    }

    fun updataPlayerInfo(player: Player) {
        mCurPlayer = player
        EventBus.getDefault().post(PlayerInfoChangeEvent(player))
    }

    fun updataGameInfo(gameInfo: GameInfo) {
        mCurGameInfo = gameInfo
        EventBus.getDefault().post(GameInfoChangeEvent(gameInfo))
    }

    private fun updataGameStatus(status: GameStatus) {
        MLog.info(TAG, "updata game status ,$mGameStatus to $status")
        mGameStatus = status
    }

    //=============================下面是接口实现=========================

    override fun getPlayer(): Player? {
        return mCurPlayer
    }

    override fun getGameInfo(): GameInfo? {
        return mCurGameInfo
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