package com.hutcwp.game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hutcwp.game.R
import com.hutcwp.game.bean.Role
import com.hutcwp.game.ui.BaseComponent
import com.hutcwp.game.ui.view.RoleView

/**
 *
 * Created by hutcwp on 2019-06-24 20:20
 *
 *
 *
 **/
class MapComponent private constructor() : BaseComponent() {

    private var mIvEnemy: RoleView? = null

    companion object {
        fun newInstance(): MapComponent {
            return MapComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_layout_main_map, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(rootView: View?) {
        mIvEnemy = rootView?.findViewById(R.id.ivEnemy)
        mIvEnemy?.setOnClickListener {
            val root = getRoot() as MainGameComponent
            root.components?.find {
                it is GameControllerComponent
            }?.let {

            }
        }
    }


}