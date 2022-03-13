package com.hutcwp.srw.info.battle

import com.hutcwp.srw.GameMain
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/13 8:56 PM
 *  description :
 */
class TextBattleStep(val chatMsg: MutableList<String>, val attacker: Robot, val defender: Robot) : BattleStep() {

    override fun run() {
        val stepText = "${attacker.attribute.name} 使用 ${attacker.useWeapon()?.Name} 攻击 ${defender.attribute.name}"
        val step2 = "${defender.attribute.name} 受到 ${attacker.useWeapon()?.attackValue} 伤害"
        GameMain.attack(attacker, defender)
        chatMsg.add(stepText)
        chatMsg.add(step2)
    }
}