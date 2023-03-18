package com.hutcwp.srw.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.music.BackgroundMusic
import com.hutcwp.srw.scene.BattleScene
import com.hutcwp.srw.scene.IScene
import com.hutcwp.srw.scene.MainGameScene
import com.hutcwp.srw.ui.GameCamera
import com.hutcwp.srw.util.getRawPos
import kotlinx.android.synthetic.main.activity_main_game.*
import me.hutcwp.BasicConfig

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
        playMainBGM()

        if (mainGameScene == null) {
            mainGameScene = MainGameScene()
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_container, mainGameScene!!)
                .show(mainGameScene!!)
                .commitAllowingStateLoss()
        } else {
            (mainGameScene as IScene).initWithContext(false)
            supportFragmentManager.beginTransaction()
                .hide(battleScene!!)
                .show(mainGameScene!!)
                .commitAllowingStateLoss()
        }
    }

    override fun switchBattleScene(
        isAuto: Boolean,
        leftRobot: RobotSprite,
        rightRobot: RobotSprite
    ) {
        if (battleScene == null) {
            battleScene = BattleScene(this)

            battleScene?.let {
                it.updateBattleParams(isAuto, leftRobot, rightRobot)
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_container, it)
                    .show(it)
                    .commitAllowingStateLoss()
            }
        } else {
            battleScene?.let {
                it.updateBattleParams(isAuto, leftRobot, rightRobot)
                it.initWithContext(false)
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun froze() {
        gameControllerLayout?.enable = true
    }

    override fun unfroze() {
        gameControllerLayout?.enable = false
    }

    //设置游戏手柄控制器
    override fun setGameController(gameController: IGameController) {
        gameControllerLayout?.addListener(gameController)
    }

    override fun removeGameController(gameController: IGameController) {
        gameControllerLayout?.removeListener(gameController)
    }


    private fun playMainBGM() {
        val path = "audio/music2/87.mp3"
        BackgroundMusic.getInstance(BasicConfig.getApplicationContext())
            .playBackgroundMusic(path, true)
    }



    companion object {
//        const val UNIT_MAP = 60
    }

}