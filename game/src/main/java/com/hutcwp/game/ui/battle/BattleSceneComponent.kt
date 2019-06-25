package com.hutcwp.game.ui.battle

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.DialogFragment
import com.hutcwp.game.R
import com.hutcwp.game.bean.Enemy
import com.hutcwp.game.core.BattleSystem
import com.hutcwp.game.core.GameManager
import kotlinx.android.synthetic.main.game_layout_battle_scene.*
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils

/**
 *
 * Created by hutcwp on 2019-06-25 19:17
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class BattleSceneComponent private constructor() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.apply {
                setDimAmount(0.0f)
                setBackgroundDrawableResource(R.color.transparent)
                addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                attributes.apply {
                    gravity = Gravity.CENTER
                    dimAmount = 0.0f
                    width = ResolutionUtils.convertDpToPixel(249f, context).toInt()
                    height = ResolutionUtils.convertDpToPixel(312f, context).toInt()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.game_layout_battle_scene, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBattleDetail.text = "开始战斗..."
        btnFinish.setOnClickListener {
            MLog.info(TAG,"click finish button.")
            dismiss()
        }
        Thread(Runnable {
            val player = GameManager.getInstance().getPlayer()
            val enemy = mockEnemy()
            MLog.info(TAG, "post delay")
            val result = BattleSystem.INSTANCE.battle(player!!, enemy) {
                activity?.runOnUiThread {
                    Handler().post {
                        tvBattleDetail.text = it
                        MLog.info(TAG, "text = $it")
                    }
                }
            }
            activity?.runOnUiThread {
                if (result) {
                    tvTitle.text = "战斗胜利"
                } else {
                    tvTitle.text = "战斗失败"
                }
            }
        }).start()
    }

    private fun mockEnemy(): Enemy {
        val enemy = Enemy()
        enemy.nick = "敌人"
        enemy.hp = 40
        enemy.attack = 7
        enemy.defence = 4
        enemy.speed = 4
        return enemy
    }

    companion object {

        const val TAG = "BattleSceneComponent"

        fun newInstance(): BattleSceneComponent {
            return BattleSceneComponent()
        }
    }

}