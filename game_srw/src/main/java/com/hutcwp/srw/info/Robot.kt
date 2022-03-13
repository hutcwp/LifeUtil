package com.hutcwp.srw.info

import com.hutcwp.srw.info.battle.Weapon

/**
 *  author : kevin
 *  date : 2022/3/13 4:27 PM
 *  description :
 */
class Robot(val attribute: Robot.Attributes, val weapons: List<Weapon>, val operator: Operator) : BaseInfo(attribute.type) {

    class Attributes(val code: Int,
                     val type: Int,
                     val imgId: Int,
                     val iconId: Int,
                     val level: Int = 1,
                     val name: String,
                     val move: Int,
                     val attack: Int,
                     var hp: Int,
                     val maxHp: Int = hp) {
    }
}