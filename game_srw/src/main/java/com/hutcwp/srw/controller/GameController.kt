package com.hutcwp.srw.controller

import android.widget.Toast
import com.hutcwp.srw.BattleUtil
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.view.IControllerMenu
import com.hutcwp.srw.view.MapView
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description :
 */
class GameController(private val mapView: MapView) : IControllerMenu, IGameController {

    private var curSprite: BaseSprite? = null

    private var curRobotSprite: RobotSprite? = null

    private var menuStatus: MenuStatus = MenuStatus.Normal


    init {
        mapView.gameController = this
        GameMain.initGame(mapView)
    }


    fun updateSelectSpritePos(pos: Pos) {
        GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
    }

    fun changeMapSelectStatus(status: MenuStatus) {
        menuStatus = status
    }

    fun canMoveToPos(pos: Pos): Boolean {
        return (pos.x in 0..MAP_WIDTH_SIZE) && (pos.y in 0..MAP_HEIGHT_SIZE)
    }


    override fun move() {
        changeMapSelectStatus(MenuStatus.Move)
        mapView.dismissMenu()

        val range = 3
        mapView.showMoveRange(GameMain.getSelectPos(), range)
    }

    override fun attack() {
        changeMapSelectStatus(MenuStatus.Attack)
        mapView.dismissMenu()

        val range = 3
        mapView.showAttackRange(GameMain.getSelectPos(), range)
    }

    override fun status() {
        curSprite ?: return
        when (curSprite) {
            is MapSprite -> {

            }
            is RobotSprite -> {
                mapView.post {
                    mapView.showRobotView((curSprite as RobotSprite).robot)
                }
            }
        }
    }

    override fun finish() {
        curRobotSprite?.updateMoveAvailable(false)
    }

    override fun skill() {

    }

    override fun select(sprite: BaseSprite) {
        when (menuStatus) {
            MenuStatus.Normal -> {
                mapView.selectSprite(sprite, false)
                mapView.showNormalRange()
                if (sprite is RobotSprite) {
                    curRobotSprite = sprite
                }
            }
            MenuStatus.Move -> {
                if (!mapView.canMove(sprite.pos)) {
                    return
                }
                GameMain.updateSpritePos(curSprite!!, sprite.pos)
                GameMain.updateSpritePos(GameMain.selectSprite!!, sprite.pos)
                mapView.showNormalRange()
                changeMapSelectStatus(MenuStatus.Normal)
            }
            MenuStatus.Attack -> {
                if (sprite is RobotSprite) {
                    Toast.makeText(mapView.context, "攻击它！", Toast.LENGTH_SHORT).show()
                    BattleUtil.attack(curRobotSprite!!, sprite)
                    mapView.showNormalRange()
                    changeMapSelectStatus(MenuStatus.Normal)

                }
            }
        }

        curSprite = sprite
        status()
    }


    override fun up() {
        MLog.debug(TAG, "up")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y - 1)
        if (canMoveToPos(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun down() {
        MLog.debug(TAG, "down")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y + 1)
        if (canMoveToPos(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun left() {
        MLog.debug(TAG, "left")
        val pos = Pos(GameMain.selectSprite!!.pos.x - 1, GameMain.selectSprite!!.pos.y)
        if (canMoveToPos(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun right() {
        MLog.debug(TAG, "right")
        val pos = Pos(GameMain.selectSprite!!.pos.x + 1, GameMain.selectSprite!!.pos.y)
        if (canMoveToPos(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
    }

    override fun ok() {
        MLog.debug(TAG, "ok")
        when (menuStatus) {
            MenuStatus.Normal -> {
                GameMain.findRobotByPos(GameMain.getSelectPos())?.let {
                    select(it)
                }
            }
            MenuStatus.Move -> {
                GameMain.findMapByPos(GameMain.getSelectPos())?.let {
                    select(it)
                }
            }
        }
    }

    override fun cancel() {
        MLog.debug(TAG, "cancel")
        resetToNormalStatus()
    }

    private fun resetToNormalStatus() {
        changeMapSelectStatus(MenuStatus.Normal)
        mapView.showNormalRange()
    }


    companion object {
        const val TAG = "GameController"
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16
    }
}