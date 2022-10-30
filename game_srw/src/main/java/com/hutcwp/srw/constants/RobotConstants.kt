package com.hutcwp.srw.constants

import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot

/**
 * author : kevin
 * date : 2022/3/20 11:37 PM
 * description :
 */
object RobotConstants {

    //阵营
    const val TEAM_RED = 0
    const val TEAM_BLUE = 1

    //机器人类型
    const val TYPE_WEA = 0
    const val TYPE_LAND = 1
    const val TYPE_AIR = 2


    //蓝色方阵营
    val GangDa = Robot.Attributes(TEAM_BLUE, TYPE_LAND, R.drawable.r_img_126, R.drawable.r_ic_0,
            1, "刚达", 6, 70, 55, 72, 0, 320, 320)
    val GaiTa = Robot.Attributes(TEAM_BLUE, TYPE_LAND, R.drawable.r_img_2, R.drawable.r_ic_4,
            1, "盖塔1", 7, 90, 55, 55, 0, 310, 310)
    val JinZ = Robot.Attributes(TEAM_BLUE, TYPE_LAND, R.drawable.r_img_29, R.drawable.r_ic_2,
            1, "金Z", 5, 85, 70, 45, 0, 360, 360)


    //红色方阵营
    val ZhaKe = Robot.Attributes(TEAM_RED, TYPE_LAND, R.drawable.r_img_54, R.drawable.i_ic_red_32,
            1, "扎克", 4, 40, 28, 55, 0, 180, 180)

    val ZhaKe1 = Robot.Attributes(TEAM_RED, TYPE_LAND, R.drawable.r_img_54, R.drawable.i_ic_red_32,
            1, "扎克", 4, 40, 28, 55, 0, 180, 180)

    val ZhaKe2 = Robot.Attributes(TEAM_RED, TYPE_LAND, R.drawable.r_img_54, R.drawable.i_ic_red_32,
            1, "扎克", 4, 40, 28, 55, 0, 180, 180)


}