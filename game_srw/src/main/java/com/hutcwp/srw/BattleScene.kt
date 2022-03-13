package com.hutcwp.srw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.srw.bean.BaseSprite
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.info.battle.BattleStep
import com.hutcwp.srw.info.battle.TextBattleStep
import com.hutcwp.srw.view.IControllerMenu
import kotlinx.android.synthetic.main.layout_scene_battle.*
import java.util.*

/**
 *  author : kevin
 *  date : 2022/3/13 1:55 PM
 *  description :
 */
class BattleScene(private val sceneSwitch: ISceneSwitch) : Fragment(), IGameController {

    var chatMsg: MutableList<String> = mutableListOf("战斗开始")

    var curIndex = 0

    var leftRobot: Robot? = null
    var rightRobot: Robot? = null

    var firstAction = true //是否先手

    var battleStepQueue: Queue<BattleStep> = LinkedList<BattleStep>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.layout_scene_battle, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        (activity as MainGameActivity).setGameController(this)
        curIndex = 0
        initBattleInfo()

        createBattleStep()

        runAllTask()

        showChatMsg()
    }

    private fun runAllTask() {
        while (battleStepQueue.isNotEmpty()) {
            battleStepQueue.poll()?.run()
        }
    }

    /**
     * 生成战斗步骤
     */
    private fun createBattleStep() {
        leftRobot ?: return
        rightRobot ?: return

        var attacker: Robot? = null
        var defender: Robot? = null
        if (firstAction) {
            attacker = rightRobot
            defender = leftRobot
        } else {
            attacker = leftRobot
            defender = rightRobot
        }


        battleStepQueue.add(attack(attacker!!, defender!!))
        if (GameMain.isAlive(defender)) {
            battleStepQueue.add(attack(defender, attacker))
        }

    }


    fun attack(attacker: Robot, defender: Robot): TextBattleStep {
        return TextBattleStep(chatMsg, attacker, defender)
    }


    private fun initBattleInfo() {
        ly_battle_scene?.updateRobots(leftRobot!!, rightRobot!!)
        ly_battle_detail?.updateRobots(leftRobot!!, rightRobot!!)
    }

    fun showChatMsg() {
        if (curIndex <= chatMsg.lastIndex) {
            ly_battle_detail?.setChatMsg(chatMsg.get(curIndex))
            curIndex++
        } else {
            sceneSwitch.switchMainScene()
        }
    }

    fun updateRobots(firstAction: Boolean = true, leftRobot: Robot, rightRobot: Robot) {
        this.firstAction = true
        this.leftRobot = leftRobot
        this.rightRobot = rightRobot
    }


    fun turnNextStep() {

    }

    override fun up() {
    }

    override fun down() {
    }

    override fun left() {
    }

    override fun right() {
    }

    override fun ok() {
        showChatMsg()
    }

    override fun cancel() {
    }


    companion object {
        const val TAG = "MainGameScene"
    }

}