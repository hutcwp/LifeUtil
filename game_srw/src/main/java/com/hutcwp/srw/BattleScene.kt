package com.hutcwp.srw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.BattleController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.Operator
import com.hutcwp.srw.info.battle.Weapon
import com.hutcwp.srw.music.BackgroundMusic
import kotlinx.android.synthetic.main.layout_scene_battle.*
import me.hutcwp.BaseConfig

/**
 *  author : kevin
 *  date : 2022/3/13 1:55 PM
 *  description :
 */
class BattleScene(private val sceneSwitch: ISceneSwitch) : Fragment() {


    private var leftRobot: RobotSprite? = null
    private var rightRobot: RobotSprite? = null
    private var isAuto = false

    private var battleController: BattleController? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_scene_battle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWithContext(true)
    }

    fun initWithContext(initFirst: Boolean) {
        if (initFirst) {
            initSceneFirst()
        }

        battleController = BattleController(this, this.leftRobot!!, this.rightRobot!!)
        battleController?.initBattle(isAuto)
        //设置手柄控制器
        (activity as MainGameActivity).setGameController(battleController!!)
    }

    /**
     * 初始化数据，视图第一次创建的时候，特殊处理
     */
    private fun initSceneFirst() {
        showChatMsg("开始战斗...")
        updateRobotInfo(leftRobot!!, rightRobot!!)
        battleController?.initBattle(true)
    }

    /**
     * 初始化战斗参数
     * @param isAuto 是否玩家主动发起
     * @param leftRobot 左边的机器人
     * @param rightRobot 右边的机器人
     */
    fun updateBattleParams(isAuto: Boolean, leftRobot: RobotSprite, rightRobot: RobotSprite) {
        this.isAuto = isAuto
        this.leftRobot = leftRobot
        this.rightRobot = rightRobot
    }

    /**
     * 更新对战血条信息
     */
    fun updateBattleInfo(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        updateRobotImg(leftRobot, rightRobot)
        updateRobotInfo(leftRobot, rightRobot)
    }

    /**
     * 更新对战机器人图片
     */
    fun updateRobotImg(leftRobot: RobotSprite, rightRobot: RobotSprite) {
        ly_battle_scene?.updateRobots(leftRobot.robot, rightRobot.robot)
    }

    /**
     * 更新机器人信息，血条之类的
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
     * 播放攻击动画
     */
    fun showAttackAnim(robotSprite: RobotSprite, weapon: Weapon, task: ITask) {
        if (robotSprite == leftRobot) {
            showLeftAttackAnim(robotSprite, weapon, task)
        } else {
            showRightAttackAnim(robotSprite, weapon, task)
        }
    }

    private fun showRightAttackAnim(rightRobot: RobotSprite, weapon: Weapon, task: ITask) {
        ly_battle_scene?.showWeaponAnim(false, weapon, task)
    }

    private fun showLeftAttackAnim(robotSprite: RobotSprite, weapon: Weapon, task: ITask) {
        ly_battle_scene?.showWeaponAnim(true, weapon, task)
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


    companion object {
        const val TAG = "MainGameScene"
    }

}