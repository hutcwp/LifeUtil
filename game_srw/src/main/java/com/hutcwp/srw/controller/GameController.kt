package com.hutcwp.srw.controller

import android.content.Intent
import android.os.Bundle
import com.hutcwp.RobotInfoActivity
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.BaseSprite
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.view.IControllerMenu
import com.hutcwp.srw.view.MapView
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description :
 */
class GameController(private val sceneSwitch: ISceneSwitch, private val mapView: MapView) : IControllerMenu, IGameController {


    private var curRobotSprite: RobotSprite? = null

    private var menuStatus: MenuStatus = MenuStatus.Normal


    init {
        mapView.gameController = this
        GameMain.switchLevel(mapView, 1)
    }


    private fun changeMapSelectStatus(status: MenuStatus) {
        menuStatus = status
    }


    private fun resetToNormalStatus() {
        changeMapSelectStatus(MenuStatus.Normal)
        mapView.resetNormalMap()
    }


    override fun move() {
        changeMapSelectStatus(MenuStatus.Move)
        mapView.dismissMenu()

        val range = curRobotSprite!!.robot.attribute.move
        mapView.showMoveRange(curRobotSprite!!.pos, range)
    }

    override fun attack() {
        changeMapSelectStatus(MenuStatus.Attack)
        mapView.dismissMenu()

        val range = curRobotSprite!!.robot.attribute.move
        mapView.showAttackRange(curRobotSprite!!.pos, range)
    }

    override fun status() {
        curRobotSprite ?: return

        val intent = Intent(mapView.context, RobotInfoActivity::class.java)
        val bundle = Bundle().apply {
            this.putSerializable(RobotInfoActivity.PARAM_ROBOT, curRobotSprite!!.robot)
        }
        intent.putExtras(bundle)

        mapView.context.startActivity(intent)
    }

    override fun finish() {
//        curRobotSprite?.updateMoveAvailable(false)
        mapView.dismissMenu()
        GameMain.takeTurn()
    }

    override fun skill() {

    }

    override fun select(sprite: BaseSprite) {

        when (menuStatus) {
            MenuStatus.Normal -> {
                if (sprite is RobotSprite) {
                    mapView.showControllerMenuDialog(sprite)
                    curRobotSprite = sprite
                }
            }
            MenuStatus.Move -> {
                if (!mapView.canMove(sprite.pos)) {
                    return
                }

                GameMain.updateSpritePos(curRobotSprite!!, sprite.pos)
                GameMain.updateSpritePos(GameMain.selectSprite!!, sprite.pos)
                mapView.resetNormalMap()
                changeMapSelectStatus(MenuStatus.Normal)
            }
            MenuStatus.Attack -> {
                if (sprite == curRobotSprite) {
                    return
                }

                if (sprite is RobotSprite) {
                    mapView.resetNormalMap()
                    changeMapSelectStatus(MenuStatus.Normal)

                    sceneSwitch.switchBattleScene(true, sprite, curRobotSprite!!)
                    curRobotSprite!!.updateAction(false)
                }
            }
        }

    }


    override fun up() {
        MLog.debug(TAG, "up")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y - 1)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
        changeMapPos(false)
    }

    override fun down() {
        MLog.debug(TAG, "down")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y + 1)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }

        changeMapPos(true)
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
            MenuStatus.Attack -> {
                GameMain.findRobotByPos(GameMain.getSelectPos())?.let {
                    select(it)
                }
            }
        }
    }

    override fun cancel() {
        MLog.debug(TAG, "cancel")
        resetToNormalStatus()
    }


    private fun changeMapPos(isDown: Boolean) {
        if (isDown) {
            if (mapView.bottom - (mapView.scrollY) > ResolutionUtils.getScreenHeight(mapView.context)) {
                mapView.scrollY += MAP_MOVE_SPEED
            }
        } else {
            if (mapView.top + (mapView.scrollY) > 0) {
                mapView.scrollY -= MAP_MOVE_SPEED
            }
        }
    }


    companion object {
        const val TAG = "GameController"
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16

        const val MAP_MOVE_SPEED = 30
    }
}