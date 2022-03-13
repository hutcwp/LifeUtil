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
import com.hutcwp.srw.view.IControllerMenu
import kotlinx.android.synthetic.main.layout_scene_battle.*

/**
 *  author : kevin
 *  date : 2022/3/13 1:55 PM
 *  description :
 */
class BattleScene(private val sceneSwitch: ISceneSwitch) : Fragment(), IGameController {

    val chatMsg: List<String> = listOf("来玩啊", "走着瞧！")

    var curIndex = 0

    var leftRobot: Robot? = null
    var rightRobot: Robot? = null


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
        showChatMsg()
        initBattleInfo()
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

    fun updateRobots(leftRobot: Robot, rightRobot: Robot) {
        this.leftRobot = leftRobot
        this.rightRobot = rightRobot
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