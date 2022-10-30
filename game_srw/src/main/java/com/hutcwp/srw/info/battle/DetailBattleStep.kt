package com.hutcwp.srw.info.battle

import com.hutcwp.srw.bean.RobotSprite

/**
 * author : kevin
 * date : 2022/3/20 12:29 AM
 * description : 攻击者，攻击武器，反击者，反击武器
 */
class DetailBattleStep(type: Int,
                       attacker: RobotSprite,
                       attackerWeapon: Weapon,
                       defender: RobotSprite,
                       defendWeapon: Weapon) : BattleStep(type, attacker, defender) {

}