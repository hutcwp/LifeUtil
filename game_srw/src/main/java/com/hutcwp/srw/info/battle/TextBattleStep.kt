//package com.hutcwp.srw.info.battle
//
//import com.hutcwp.srw.GameMain
//import com.hutcwp.srw.bean.RobotSprite
//
///**
// *  author : kevin
// *  date : 2022/3/13 8:56 PM
// *  description :
// */
//class TextBattleStep(val chatMsg: MutableList<String>, val attacker: RobotSprite, val defender: RobotSprite) : BattleStep() {
//
//    override fun run() {
//        val stepText = "${attacker.robot.attribute.name} 使用 ${attacker.useWeapon()?.Name} 攻击 ${defender.robot.attribute.name}"
//        val step2 = "${defender.robot.attribute.name} 受到 ${attacker.useWeapon()?.attackValue} 伤害"
//        GameMain.attack(attacker, defender)
//        chatMsg.add(stepText)
//        chatMsg.add(step2)
//    }
//}