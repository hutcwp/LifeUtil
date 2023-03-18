package com.hutcwp.srw.controller

import android.content.Intent
import android.os.Bundle
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.ui.activity.RobotInfoActivity
import com.hutcwp.srw.ui.view.IControllerMenu
import com.hutcwp.srw.ui.view.MapView

/**
 *  author : kevin
 *  date : 2022/11/10 21:08
 *  description : 控制菜单
 */
class GameControllerMenu(
    private val sceneSwitch: ISceneSwitch,
    private val menuContainer: IMenuContainer,
    private val mapView: MapView
) : IControllerMenu {

    private var curRobotSprite: RobotSprite? = null
    private var menuStatus: MenuStatus = MenuStatus.Normal

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

        showRobotStatus(curRobotSprite!!.robot)
    }

    private fun showRobotStatus(robot: Robot) {
        val context = mapView.context
        val intent = Intent(context, RobotInfoActivity::class.java).apply {
            val bundle = Bundle().apply {
                this.putSerializable(RobotInfoActivity.PARAM_ROBOT, robot)
            }
            this.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    override fun finish() {
        curRobotSprite?.updateMoveAvailable(false)
        menuContainer.dismissMenu()
        GameMain.takeTurn()
    }

    override fun skill() {

    }

    override fun select() {

        when (menuStatus) {
            MenuStatus.Normal -> {
                val sprite = GameMain.findRobotByPos(GameMain.getSelectPos())
                sprite ?: return

                if (GameMain.isBlueRobot(sprite)) {
                    menuContainer.showControllerMenuDialog(sprite)
                    curRobotSprite = sprite
                } else {
                    showRobotStatus(sprite.robot)
                }
            }

            MenuStatus.Move -> {
                val sprite = GameMain.selectSprite
                sprite?:return

                if (!mapView.canMove(sprite.pos)) {
                    return
                }

                GameMain.updateSpritePos(curRobotSprite!!, sprite.pos)
                GameMain.updateSpritePos(GameMain.selectSprite!!, sprite.pos)
                mapView.resetNormalMap()
                changeMapSelectStatus(MenuStatus.Normal)
            }

            MenuStatus.Attack -> {
                val sprite = GameMain.findRobotByPos(GameMain.getSelectPos())
                sprite ?: return
                if (sprite == curRobotSprite) {
                    return
                }

                mapView.resetNormalMap()
                changeMapSelectStatus(MenuStatus.Normal)

                sceneSwitch.switchBattleScene(true, sprite, curRobotSprite!!)
                curRobotSprite!!.updateAction(false)
            }
        }
    }

    override fun cancel() {
        resetToNormalStatus()
    }


}