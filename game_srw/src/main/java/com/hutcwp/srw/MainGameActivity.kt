package com.hutcwp.srw

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.music.BackgroundMusic
import kotlinx.android.synthetic.main.activity_main_game.*

@Route(path = "/game/srw")
class MainGameActivity : AppCompatActivity(), ISceneSwitch {


    var mainGameScene: MainGameScene? = null
    var battleScene: BattleScene? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        switchMainScene()

//        Handler(Looper.getMainLooper()).postDelayed({
//            switchBattleScene(true, GameMain.robotSpriteList[1], GameMain.robotSpriteList[0])
//        }, 1000)

//        playBGM()
    }

    private fun playBGM() {
        val path = "audio/music2/87.mp3"
        BackgroundMusic.getInstance(this).playBackgroundMusic(path, true)
    }


    override fun switchMainScene() {
        if (mainGameScene == null) {
            mainGameScene = MainGameScene()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fl_container, mainGameScene!!)
                    .show(mainGameScene!!)
                    .commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                    .hide(battleScene!!)
                    .show(mainGameScene!!)
                    .commitAllowingStateLoss()

        }

    }

    override fun switchBattleScene(isAuto: Boolean, leftRobot: RobotSprite, rightRobot: RobotSprite) {

        if (battleScene == null) {
            battleScene = BattleScene(this)

            battleScene?.let {
                it.initRobots(isAuto, leftRobot, rightRobot)

                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_container, it)
                        .show(it)
                        .commitAllowingStateLoss()
            }

        } else {
            battleScene?.let {
                it.initRobots(isAuto, leftRobot, rightRobot)

                supportFragmentManager.beginTransaction()
                        .hide(it)
                        .show(it)
                        .commitAllowingStateLoss()
            }
        }
    }

    fun setGameController(gameController: IGameController) {
        gcLayout?.gameController = gameController
    }


    companion object {
        const val UNIT_MAP = 60
    }

}