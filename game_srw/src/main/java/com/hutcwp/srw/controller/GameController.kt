package com.hutcwp.srw.controller

import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.ui.view.MapView
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description : 游戏控制器
 *  实现IGameController手柄接口
 */
class GameController(
    private val sceneSwitch: ISceneSwitch,
    private val gameMenuController: GameControllerMenu,
    private val mapView: MapView
) : IGameController {



    init {
        GameMain.switchLevel(mapView, sceneSwitch, 1)
    }


    /**
     * 手柄控制器回调
     */
    override fun up() {
        MLog.debug(TAG, "up")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y - 1)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun down() {
        MLog.debug(TAG, "down")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y + 1)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun left() {
        MLog.debug(TAG, "left")
        val pos = Pos(GameMain.selectSprite!!.pos.x - 1, GameMain.selectSprite!!.pos.y)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun right() {
        MLog.debug(TAG, "right")
        val pos = Pos(GameMain.selectSprite!!.pos.x + 1, GameMain.selectSprite!!.pos.y)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun ok() {
        MLog.debug(TAG, "ok")
        gameMenuController.select()
    }

    override fun cancel() {
        MLog.debug(TAG, "cancel")
        gameMenuController.cancel()
    }


    companion object {
        const val TAG = "GameController"
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16
    }

}