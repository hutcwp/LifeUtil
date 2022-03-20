package com.hutcwp.srw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.BattleController
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.battle.Weapon
import com.hutcwp.srw.music.BackgroundMusic
import kotlinx.android.synthetic.main.layout_scene_battle.*
import me.hutcwp.BaseConfig

/**
 *  author : kevin
 *  date : 2022/3/13 1:55 PM
 *  description :
 */
class BattleScene(private val sceneSwitch: ISceneSwitch) : Fragment(), IGameController {


    private var leftRobot: RobotSprite? = null
    private var rightRobot: RobotSprite? = null

    private var battleController: BattleController? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_scene_battle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置手柄控制器
        (activity as MainGameActivity).setGameController(this)
        showChatMsg("开始战斗...")

    }

    /**
     * 更新对战血条信息
     */
    fun updateBattleInfo(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        ly_battle_scene?.updateRobots(leftRobot.robot, rightRobot.robot)
        ly_battle_detail?.updateRobots(leftRobot.robot, rightRobot.robot)
    }


    fun showChatMsg(msg: String) {
        ly_battle_detail?.setChatMsg(msg)
    }

    /**
     * 初始化战斗
     */
    fun initRobots(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        this.leftRobot = leftRobot
        this.rightRobot = rightRobot

        battleController = BattleController(this, leftRobot, rightRobot)
        battleController?.initBattle()
    }

    fun showAttackAnim(robotSprite: RobotSprite, weapon: Weapon) {
        if (robotSprite == leftRobot) {
            showLeftAttackAnim(robotSprite, weapon)
        } else {
            showRightAttackAnim(robotSprite, weapon)
        }
    }

    private fun showRightAttackAnim(rightRobot: RobotSprite, weapon: Weapon) {
        ly_battle_scene?.showWeaponAnim(false)
    }

    private fun showLeftAttackAnim(robotSprite: RobotSprite, weapon: Weapon) {
        ly_battle_scene?.showWeaponAnim(true)
    }

    /**
     * 战斗结束，回收工作
     */
    fun onFinish() {
        this.leftRobot = null
        this.rightRobot = null
        this.battleController = null
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).stopBackgroundMusic()
        sceneSwitch.switchMainScene()
    }


//    =====================操作控制器==============

    override fun up() {
    }

    override fun down() {
    }

    override fun left() {
    }

    override fun right() {
    }

    override fun ok() {
        battleController?.startPlayBattle()
    }

    override fun cancel() {
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}