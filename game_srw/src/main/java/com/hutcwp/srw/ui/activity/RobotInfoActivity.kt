package com.hutcwp.srw.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.constants.RobotConstants
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.ui.view.info.RobotInfoAdapter
import com.hutcwp.srw.util.*
import kotlinx.android.synthetic.main.activity_robot_info.*

class RobotInfoActivity : AppCompatActivity(), IGameController {

    var robot: Robot? = null

    private val adapter: RobotInfoAdapter by lazy { RobotInfoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.setBackgroundColor(Color.BLACK)
        setContentView(R.layout.activity_robot_info)

        robot = intent?.extras?.get(PARAM_ROBOT) as Robot?
        robot ?: return

        updateRobotInfo(robot!!)
        changeUIStyle(robot!!.attribute.team)

        gcLayout?.gameController = this
    }

    //如果是红色方，机器人图片在左边
    private fun changeUIStyle(type: Int) {
        if (type == RobotConstants.TEAM_RED) {
            val lp = iv_robot.layoutParams as ConstraintLayout.LayoutParams
            lp.startToStart = R.id.cl_container
            iv_robot.layoutParams = lp

            val lp2 = cl_basic_info.layoutParams as ConstraintLayout.LayoutParams
            lp2.startToStart = -1
            lp2.endToEnd = R.id.cl_container
            cl_basic_info.layoutParams = lp2
        }
    }


    private fun updateRobotInfo(robot: Robot) {

        viewPager?.adapter = adapter
        adapter?.setData(robot)

        robot.attribute.let {
            tv_robot_name?.text = it.robotNameStr()
            tv_level?.text = it.levelStr()
            tv_type?.text = it.typeStr()
            tv_mobility?.text = it.mobilityStr()
            tv_skill?.text = it.skillStr()

            iv_robot?.setImageResource(it.imgId)
        }

        robot.operator.let {
            tv_operator_name?.text = "驾驶员   ${it.name}"
            iv_operator_avatar?.setImageResource(it.resId)
        }
    }

    fun addGameListener(l: IGameController) {
        gcLayout.addListener(l)
    }

    override fun up() {
    }

    override fun down() {
    }

    override fun left() {
        viewPager?.currentItem = 0
    }

    override fun right() {
        viewPager?.currentItem = 1
    }

    override fun ok() {
    }

    override fun cancel() {
    }


    companion object {
        const val PARAM_ROBOT = "param_robot"
    }
}