package com.hutcwp.srw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.activity_main_game.*

@Route(path = "/game/srw")
class MainGameActivity : AppCompatActivity(), ISceneSwitch {


    var mainGameScene: MainGameScene? = null
    var battleScene: BattleScene? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        switchMainScene()
    }


    override fun switchMainScene() {
        if (mainGameScene == null) {
            mainGameScene = MainGameScene()
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, mainGameScene!!)
                .commitAllowingStateLoss()
    }

    override fun switchBattleScene(leftRobot: Robot, rightRobot: Robot) {
        if (battleScene == null) {
            battleScene = BattleScene(this).apply {
                this.updateRobots(leftRobot, rightRobot)
            }
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, battleScene!!)
                .commitAllowingStateLoss()
    }

    fun setGameController(gameController: IGameController) {
        gcLayout?.gameController = gameController
    }


    companion object {
        const val UNIT_MAP = 60

        const val SCENE_BATTLE = 0
        const val SCENE_MAIN_MAP = 1
    }

}