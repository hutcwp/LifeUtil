package com.hutcwp.srw.compute

import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.battle.Weapon

/**
 *  author : kevin
 *  date : 2022/3/24 10:17 PM
 *  description :
 */
object BattleCenter {

    /**
     * 计算出战斗受的伤害
     * 伤害值 = 攻击者力量+武器类型伤害 - 防御者防御力
     */
    fun attackValue(attack: RobotSprite, defend: RobotSprite, weapon: Weapon): Int {
//        when(defend) //todo 要计算当前所处的地形
        var value =  weapon.attackValue - defend.robot.attribute.defend
        value = 0.coerceAtLeast(value)
        return value
    }

}