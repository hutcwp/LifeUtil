package com.hutcwp.srw.info

import com.hutcwp.srw.info.battle.Weapon
import java.io.Serializable

/**
 *  author : kevin
 *  date : 2022/3/13 4:27 PM
 *  description :
 */
class Robot(val attribute: Robot.Attributes, val weapons: List<Weapon>, val operator: Operator) : BaseInfo(attribute.type), Serializable {

    class Attributes(val code: Int, //阵营 0红色阵营 1蓝色阵营
                     var type: Int, // 0海，1陆 ，2空
                     val imgId: Int, //机器人图片
                     val iconId: Int, //机器人地图图标
                     var level: Int = 1, //等级
                     val name: String, //名字
                     var move: Int, //移动力
                     var attack: Int, //伤害
                     var defend: Int, //防御
                     var speed: Int, //速度
                     var exp: Int = 0,
                     var hp: Int, //血量
                     val maxHp: Int = hp//最大血量值


    ) : Serializable {
    }


}