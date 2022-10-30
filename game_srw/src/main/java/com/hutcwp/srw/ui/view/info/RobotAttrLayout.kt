package com.hutcwp.srw.ui.view.info

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.util.*
import kotlinx.android.synthetic.main.layout_attr_info.view.*

/**
 *  author : kevin
 *  date : 2022/4/10 6:30 PM
 *  description :
 */
class RobotAttrLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_attr_info, this)
    }


    fun updateRobotInfo(robot: Robot) {
        robot.attribute.let {
            tv_strength?.text = it.strengthStr()
            tv_defensive?.text = it.defendStr()
            tv_speed?.text = it.speedStr()
            tv_hp?.text = it.hpStr()
            tv_exp?.text = it.expStr()
            tv_up_exp?.text = it.upExpStr()
        }
    }

}