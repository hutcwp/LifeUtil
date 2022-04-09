package com.hutcwp.srw.service

import android.content.Context
import com.hutcwp.srw.bean.MapSprite
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.bean.SelectSprite
import com.hutcwp.srw.constants.LevelConfig
import com.hutcwp.srw.controller.TestMockData

/**
 *  author : kevin
 *  date : 2022/4/9 2:39 PM
 *  description : 关卡管理器
 */
object LevelManager {

    private val dataMock = TestMockData()


    fun getMapSprite(context: Context): MutableList<MapSprite> {
        return dataMock.createMapListFromLevel(context, LevelConfig.No1.mapList)
    }

    fun getRobotSprite(): List<RobotSprite> {
        return LevelConfig.No1.blueRobotList + LevelConfig.No1.redRobotList
    }

    fun getSelectSprite(context: Context): SelectSprite {
        return dataMock.createSelectSpriteFromPos(context, LevelConfig.No1.blueRobotList[0].pos)
    }

}