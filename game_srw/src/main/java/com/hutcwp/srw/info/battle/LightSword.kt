package com.hutcwp.srw.info.battle

/**
 *  author : kevin
 *  date : 2022/3/13 4:18 PM
 *  description :
 */
class LightSword : Weapon("光剑", 120, 1,120) {

    override fun weaAttack(): Int {
        return super.weaAttack() - 20
    }
}