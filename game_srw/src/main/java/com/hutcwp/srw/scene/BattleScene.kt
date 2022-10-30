package com.hutcwp.srw.scene

import android.view.View
import com.hutcwp.srw.controller.ITask
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.BattleController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.Operator
import com.hutcwp.srw.info.battle.Weapon
import com.hutcwp.srw.music.BackgroundMusic
import kotlinx.android.synthetic.main.layout_scene_battle.*
import me.hutcwp.BaseConfig
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/13 1:55 PM
 *  description : 战斗场景
 */
class BattleScene(private val sceneSwitch: ISceneSwitch) : BaseScene(R.layout.layout_scene_battle), IScene {


    private var leftRobot: RobotSprite? = null
    private var rightRobot: RobotSprite? = null
    private var isUserCmd = false

    private var battleController: BattleController? = null

    /**
     * 初始化战斗参数
     * @param isAuto 是否玩家主动发起
     * @param leftRobot 左边的机器人
     * @param rightRobot 右边的机器人
     */
    fun updateBattleParams(isAuto: Boolean, leftRobot: RobotSprite, rightRobot: RobotSprite) {
        this.isUserCmd = isAuto
        this.leftRobot = leftRobot
        this.rightRobot = rightRobot
    }


    override fun firstInitView(rootView: View) {
    }

    override fun initData() {
        MLog.debug(TAG, "initData")
        initBattleInfo(leftRobot!!, rightRobot!!)
        battleController = BattleController(this, this.leftRobot!!, this.rightRobot!!)
        battleController?.initBattle(isUserCmd)
        //设置手柄控制器
        (activity as ISceneSwitch).setGameController(battleController!!)
    }

    /**
     * 更新对战信息
     */
    private fun initBattleInfo(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        updateRobotImg(leftRobot, rightRobot)
        updateRobotInfo(leftRobot, rightRobot)
    }

    /**
     * 更新对战机器人图片
     */
    private fun updateRobotImg(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        ly_battle_scene?.updateRobots(leftRobot.robot, rightRobot.robot)
    }

    /**
     * 更新机器人对战信息（命中率，血条等）
     */
    fun updateRobotInfo(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        ly_battle_detail?.updateRobots(leftRobot.robot, rightRobot.robot)
    }

    /**
     * 展示聊天信息
     */
    fun showChatMsg(msg: String, resId: Int = -1) {
        ly_battle_detail?.setChatMsg(resId, msg)
    }

    /**
     * 展示聊天信息
     */
    fun showChatMsg(msg: String, operator: Operator) {
        ly_battle_detail?.setChatMsg(operator.resId, msg)
    }

    /**
     * 展示下一个独白的提示
     */
    fun showNextTip(show: Boolean) {
        ly_battle_detail?.showNextIcon(show)
    }

    /**
     * 播放攻击动画
     */
    fun showAttackAnim(robotSprite: RobotSprite, weapon: Weapon, task: ITask) {
        if (robotSprite == leftRobot) {
            showLeftAttackAnim(weapon, task)
        } else {
            showRightAttackAnim(weapon, task)
        }
    }

    private fun showRightAttackAnim(weapon: Weapon, task: ITask) {
        ly_battle_scene?.showWeaponAnim(false, weapon, task)
    }

    private fun showLeftAttackAnim(weapon: Weapon, task: ITask) {
        ly_battle_scene?.showWeaponAnim(true, weapon, task)
    }

    fun switchMainScene() {
        sceneSwitch.switchMainScene()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        MLog.debug(TAG, "hide?=$hidden")

        if (hidden) {
            MLog.debug(TAG, "hide")
            recycle()
            battleController?.recycle()
        }
    }

    /**
     * 战斗结束，回收工作
     */
    private fun recycle() {
        this.leftRobot = null
        this.rightRobot = null
        this.battleController = null
        this.isUserCmd = false
        this.battleController = null
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).stopBackgroundMusic()
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}