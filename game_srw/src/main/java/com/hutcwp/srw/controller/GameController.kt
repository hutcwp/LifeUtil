package com.hutcwp.srw.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.BaseSprite
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.ui.GameCamera
import com.hutcwp.srw.ui.activity.RobotInfoActivity
import com.hutcwp.srw.ui.view.IControllerMenu
import com.hutcwp.srw.ui.view.MapView
import com.hutcwp.srw.util.getRawPos
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description : 游戏控制器
 *  实现IGameController手柄接口
 */
class GameController(
    private val sceneSwitch: ISceneSwitch,
    private val menuContainer: IMenuContainer,
    private val mapView: MapView,
    private val cameraView: View
) : IControllerMenu, IGameController {

    private var curRobotSprite: RobotSprite? = null
    private var menuStatus: MenuStatus = MenuStatus.Normal



    init {
        GameMain.switchLevel(mapView, sceneSwitch, 1)
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
        menuContainer.dismissMenu()

        val range = curRobotSprite!!.robot.attribute.move
        mapView.showMoveRange(curRobotSprite!!.pos, range)
    }

    override fun attack() {
        changeMapSelectStatus(MenuStatus.Attack)
        menuContainer.dismissMenu()

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
        menuContainer.dismissMenu()
        GameMain.takeTurn()
    }

    override fun skill() {

    }

    override fun select(sprite: BaseSprite) {
        when (menuStatus) {
            MenuStatus.Normal -> {
                if (sprite is RobotSprite) {
                    menuContainer.showControllerMenuDialog(sprite)
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

//        gameCamera.up()
    }

    override fun down() {
        MLog.debug(TAG, "down")
        val pos = Pos(GameMain.selectSprite!!.pos.x, GameMain.selectSprite!!.pos.y + 1)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }
//        gameCamera.down()
    }

    override fun left() {
        MLog.debug(TAG, "left")
        val pos = Pos(GameMain.selectSprite!!.pos.x - 1, GameMain.selectSprite!!.pos.y)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }

//        gameCamera.left()
    }

    override fun right() {
        MLog.debug(TAG, "right")
        val pos = Pos(GameMain.selectSprite!!.pos.x + 1, GameMain.selectSprite!!.pos.y)
        if (mapView.posInMapRange(pos)) {
            GameMain.updateSpritePos(GameMain.selectSprite!!, pos)
        }

//        gameCamera.right()
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


    companion object {
        const val TAG = "GameController"
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16
    }
}