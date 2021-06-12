package com.hutcwp.game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hutcwp.game.R
import com.hutcwp.game.ui.BaseComponent

/**
 *
 * Created by hutcwp on 2019-06-24 20:30
 *
 *
 *
 **/
class NPCListComponent : BaseComponent() {

    companion object {
        fun newInstance(): NPCListComponent {
            return NPCListComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_layout_npc_list, container, false)
    }

}