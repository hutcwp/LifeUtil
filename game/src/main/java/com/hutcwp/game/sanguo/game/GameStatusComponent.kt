package com.hutcwp.game.sanguo.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hutcwp.game.R
import com.hutcwp.game.sanguo.core.GameManager
import com.hutcwp.game.sanguo.ui.BaseComponent
import com.hutcwp.game.sanguo.util.FontUtil
import me.hutcwp.log.MLog


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
    private var tvHp: TextView? = null
    private var tvAttack: TextView? = null
    private var tvDenfence: TextView? = null

    companion object {
        private const val TAG = "GameStatusComponent"
        fun newInstance(): GameStatusComponent {
            return GameStatusComponent()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_layout_game_status, container, false)
        initView(rootView)
        initData()
        return rootView
    }

    private fun initView(rootView: View?) {
        tvTime = rootView?.findViewById(R.id.tvTime)
        tvNick = rootView?.findViewById(R.id.tvNick)
        tvYear = rootView?.findViewById(R.id.tvYear)
        tvOld = rootView?.findViewById(R.id.tvOld)
        tvHp = rootView?.findViewById(R.id.tvHp)
        tvAttack = rootView?.findViewById(R.id.tvAttack)
        tvDenfence = rootView?.findViewById(R.id.tvDenfence)

        setFonts()
    }

    private fun setFonts() {
        FontUtil.setFont(tvTime, R.font.iconfont)
        FontUtil.setFont(tvNick, R.font.iconfont)
        FontUtil.setFont(tvYear, R.font.iconfont)
        FontUtil.setFont(tvOld, R.font.iconfont)
        FontUtil.setFont(tvHp, R.font.iconfont)
        FontUtil.setFont(tvAttack, R.font.iconfont)
        FontUtil.setFont(tvDenfence, R.font.iconfont)
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val gameInfo = GameManager.getInstance().getGameInfo()
        val player = GameManager.getInstance().getPlayer()
        MLog.info(TAG, "gameInfo = $gameInfo")
        MLog.info(TAG, "player = $player")
        tvTime?.text = "[${gameInfo?.time}年]"
        tvNick?.text = player?.nick

        tvAttack?.text = "攻击:${player?.attack}"
        tvHp?.text = "生命值: ${player?.hp}"
        tvDenfence?.text = "防御:${player?.defence}"
    }
}