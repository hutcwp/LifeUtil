package com.hutcwp.srw.scene

import android.view.View
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.controller.IMenuContainer
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.ui.activity.MainGameActivity
import com.hutcwp.srw.ui.view.ControllerMenuDialog
import com.hutcwp.srw.ui.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 1:53 PM
 *  description : 游戏主场景，带地图
 */
class MainGameScene : BaseScene(R.layout.layout_scene_main_game), IScene, IMenuContainer {

    private var gameController: GameController? = null
    private var controllerMenuDialog: ControllerMenuDialog? = null


    override fun firstInitView(rootView: View) {
        val map = rootView.findViewById<MapView>(R.id.game_map)
        gameController =
            GameController(activity as MainGameActivity, this, map)
    }

    override fun initData() {
        (activity as ISceneSwitch).setGameController(gameController!!)
    }

    /**
     * 展示控制菜单
     */
    override fun showControllerMenuDialog(sprite: RobotSprite) {
        if (controllerMenuDialog == null) {
            controllerMenuDialog = ControllerMenuDialog.newInstance(sprite.robot)
            controllerMenuDialog?.iControllerMenu = gameController
        }
        controllerMenuDialog?.updateRobot(sprite.robot)
        ControllerMenuDialog.showMenu(controllerMenuDialog!!, activity!!.supportFragmentManager)
    }

    /**
     * 隐藏菜单弹窗
     */
    override fun dismissMenu() {
        controllerMenuDialog?.dismissAllowingStateLoss()
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}