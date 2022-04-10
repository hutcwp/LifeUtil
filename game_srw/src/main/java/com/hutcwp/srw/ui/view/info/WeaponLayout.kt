package com.hutcwp.srw.ui.view.info

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.info.battle.Weapon
import com.hutcwp.srw.util.*
import kotlinx.android.synthetic.main.layout_view_weapon.view.*

/**
 *  author : kevin
 *  date : 2022/4/10 6:31 PM
 *  description : 武器面板
 */
class WeaponLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_view_weapon, this)
    }

    private var curWeapon: Weapon? = null

    fun updateRobotInfo(robot: Robot) {
        if (robot.weapons.isNotEmpty()) {
            val weapon = robot.weapons[0]
            tv_weapon1.text = weapon.name
            selectWeapon(weapon)
        }
        if (robot.weapons.size >= 2) {
            tv_weapon2.text = robot.weapons[1].name
        }
    }

    fun selectWeapon(weapon: Weapon) {
        this.curWeapon = weapon
        tv_aim?.text = weapon.aimStr()
        tv_range?.text = weapon.rangStr()
        tv_air?.text = weapon.attackAir()
        tv_land?.text = weapon.attackLand()
        tv_sea?.text = weapon.attackSea()
    }
}