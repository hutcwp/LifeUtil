package com.hutcwp.srw.scene

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.controller.GameControllerMenu
import com.hutcwp.srw.controller.IMenuContainer
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.ui.GameCamera
import com.hutcwp.srw.ui.activity.MainGameActivity
import com.hutcwp.srw.ui.dialog.ControllerMenuController
import com.hutcwp.srw.ui.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 1:53 PM
 *  description : 游戏主场景，带地图
 */
class MainGameScene : BaseScene(R.layout.layout_scene_main_game), IScene, IMenuContainer {

    private var gameController: GameController? = null

    private lateinit var gameMenuController: GameControllerMenu

    private lateinit var controllerMenuController: ControllerMenuController


    override fun firstInitView(rootView: View) {
        val mapView = rootView.findViewById<MapView>(R.id.game_map)
        val cameraView = rootView.findViewById<View>(R.id.cl_container)
        val flMenu = rootView.findViewById<ConstraintLayout>(R.id.fl_menu)


        val gameCamera = GameCamera(mapView, cameraView)
        mapView.gameCamera = gameCamera

        gameMenuController = GameControllerMenu(activity as ISceneSwitch, this, mapView)

        gameController = GameController(activity as MainGameActivity, gameMenuController, mapView)

        controllerMenuController = ControllerMenuController(flMenu, activity as ISceneSwitch).apply {
            this.iControllerMenu = gameMenuController
        }

    }

    override fun initData() {
        (activity as ISceneSwitch).setGameController(gameController!!)
    }

    /**
     * 展示控制菜单
     */
    override fun showControllerMenuDialog(sprite: RobotSprite) {
        controllerMenuController.showDialog(sprite)
    }

    /**
     * 隐藏菜单弹窗
     */
    override fun dismissMenu() {
        controllerMenuController.dismissDialog()
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}