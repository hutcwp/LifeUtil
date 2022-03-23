package com.hutcwp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.util.*
import kotlinx.android.synthetic.main.activity_robot_info.*

class RobotInfoActivity : AppCompatActivity() {

    var robot: Robot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_info)

        robot = intent?.extras?.get(PARAM_ROBOT) as Robot?
        robot ?: return

        updateRobotInfo(robot!!)
    }


    private fun updateRobotInfo(robot: Robot) {
        robot.attribute.let {
            tv_robot_name?.text = it.robotNameStr()
            tv_level?.text = it.levelStr()
            tv_type?.text = it.typeStr()
            tv_mobility?.text = it.mobilityStr()
            tv_skill?.text = it.skillStr()


            tv_strength?.text = it.strengthStr()
            tv_defensive?.text = it.defendStr()
            tv_speed?.text = it.speedStr()
            tv_hp?.text = it.hpStr()
            tv_exp?.text = it.expStr()
            tv_up_exp?.text = it.upExpStr()

            iv_robot?.setImageResource(it.imgId)
        }

        robot.operator.let {
            tv_operator_name?.text = it.name
            iv_operator_avatar?.setImageResource(it.resId)
        }
    }


    companion object {
        const val PARAM_ROBOT = "param_robot"
    }
}