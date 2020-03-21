package com.hutcwp.game.sanguo.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hutcwp.game.R
import com.hutcwp.game.sanguo.bean.Role
import com.hutcwp.game.sanguo.ui.BaseComponent
import com.hutcwp.game.sanguo.battle.BattleSceneComponent

/**
 *
 * Created by hutcwp on 2019-06-24 20:29
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameControllerComponent : BaseComponent() {

    private var mSelectRole: Role? = null

    private var btnAttack: Button? = null


    fun setSelectRole(role: Role) {
        mSelectRole = role
    }

    companion object {
        fun newInstance(): GameControllerComponent {
            return GameControllerComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_layout_controller, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(rootView: View?) {
        btnAttack = rootView?.findViewById(R.id.btnAttack)
        btnAttack?.setOnClickListener {
            val battleSceneComponent = BattleSceneComponent.newInstance()
            battleSceneComponent.show(childFragmentManager, BattleSceneComponent.TAG)
        }
    }


    fun refresh() {

    }

}