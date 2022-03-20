package com.hutcwp.srw.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.view_battle_scene.view.*

/**
 *  author : kevin
 *  date : 2022/3/13 11:16 AM
 *  description :
 */
class BattleSceneLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_battle_scene, this)
    }


    fun updateRobots(left: Robot, right: Robot) {
        iv_left?.setImageResource(left.attribute.imgId)
        iv_right?.setImageResource(right.attribute.imgId)
    }

    fun showWeaponAnim(isLeft: Boolean) {
        val imageView = ImageView(context)
        val lp = FrameLayout.LayoutParams(100, 40)
        lp.gravity = Gravity.CENTER_VERTICAL
        imageView.layoutParams = lp
        imageView.setImageResource(R.drawable.weapon_daodan)

        fl_anim?.removeAllViews()
        fl_anim?.addView(imageView)

        val x = if (isLeft) {
            floatArrayOf(30f, 120f, 240f, 300f, 400f, 560f)
        } else {
            floatArrayOf(520f, 400f, 300f, 240f, 180f, 30f)
        }
        val objectAnim: ObjectAnimator = ObjectAnimator.ofFloat(imageView, "translationX", *x)
        objectAnim.duration = 2000
        objectAnim.start()
        objectAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                fl_anim?.removeAllViews()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
    }


}