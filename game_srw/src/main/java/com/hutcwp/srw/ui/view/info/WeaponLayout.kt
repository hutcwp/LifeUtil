package com.hutcwp.srw.ui.view.info

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.info.battle.Weapon
import com.hutcwp.srw.music.GameMusicManager
import com.hutcwp.srw.ui.activity.RobotInfoActivity
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
        (context as RobotInfoActivity).addGameListener(object : IGameController {
            override fun up() {
                if (curWeapon != weapons[0]) {
                    selectWeapon(weapons[0])
                    selectWeaponIndex(true)
                    GameMusicManager.playSelectWav()
                }
            }

            override fun down() {
                if (curWeapon != weapons[1]) {
                    if (weapons.size >= 2) {
                        selectWeapon(weapons[1])
                        selectWeaponIndex(false)
                        GameMusicManager.playSelectWav()
                    }
                }
            }

            override fun left() {
            }

            override fun right() {
            }

            override fun ok() {
            }

            override fun cancel() {
            }

        })
    }

    private var curWeapon: Weapon? = null
    private var weapons: List<Weapon> = mutableListOf()

    fun updateRobotInfo(robot: Robot, isTopWeapon: Boolean) {
        weapons = robot.weapons
        selectWeaponIndex(isTopWeapon)

        if (robot.weapons.isNotEmpty()) {
            val weapon = robot.weapons[0]
            tv_weapon1.text = weapon.name
            selectWeapon(weapon)
            tv_weapon1?.visibility = View.VISIBLE
        }
        if (robot.weapons.size >= 2) {
            tv_weapon2.text = robot.weapons[1].name
            tv_weapon2?.visibility = View.VISIBLE
        }
    }

    /**
     * 更新武器属性为选中武器
     */
    fun selectWeapon(weapon: Weapon) {
        this.curWeapon = weapon
        tv_aim?.text = weapon.aimStr()
        tv_range?.text = weapon.rangStr()
        tv_air?.text = weapon.attackAir()
        tv_land?.text = weapon.attackLand()
        tv_sea?.text = weapon.attackSea()
    }

    /**
     * 更新选中的icon
     */
    fun selectWeaponIndex(isTopWeapon: Boolean) {
        if (isTopWeapon) {
            iv_select1?.visibility = View.VISIBLE
            iv_select2?.visibility = View.INVISIBLE
        } else {
            iv_select1?.visibility = View.INVISIBLE
            iv_select2?.visibility = View.VISIBLE
        }
    }
}