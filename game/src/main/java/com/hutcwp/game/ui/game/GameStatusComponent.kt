package com.hutcwp.game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hutcwp.game.R
import com.hutcwp.game.ui.BaseComponent
import com.hutcwp.game.util.FontUtil


/**
 *
 * Created by hutcwp on 2019-06-23 21:55
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameStatusComponent private constructor() : BaseComponent() {

    private var tvTime: TextView? = null
    private var tvNick: TextView? = null
    private var tvYear: TextView? = null
    private var tvOld: TextView? = null
    private var tvStrength: TextView? = null
    private var tvTroops: TextView? = null
    private var tvMoney: TextView? = null

    companion object {
        fun newInstance(): GameStatusComponent {
            return GameStatusComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_layout_game_status, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(rootView: View?) {
        tvTime = rootView?.findViewById(R.id.tvTime)
        tvNick = rootView?.findViewById(R.id.tvNick)
        tvYear = rootView?.findViewById(R.id.tvYear)
        tvOld = rootView?.findViewById(R.id.tvOld)
        tvStrength = rootView?.findViewById(R.id.tvStrength)
        tvTroops = rootView?.findViewById(R.id.tvTroops)
        tvMoney = rootView?.findViewById(R.id.tvMoney)

        setFonts()
    }

    private fun setFonts() {
        FontUtil.setFont(tvTime, R.font.iconfont)
        FontUtil.setFont(tvNick, R.font.iconfont)
        FontUtil.setFont(tvYear, R.font.iconfont)
        FontUtil.setFont(tvOld, R.font.iconfont)
        FontUtil.setFont(tvStrength, R.font.iconfont)
        FontUtil.setFont(tvTroops, R.font.iconfont)
        FontUtil.setFont(tvMoney, R.font.iconfont)
    }

}