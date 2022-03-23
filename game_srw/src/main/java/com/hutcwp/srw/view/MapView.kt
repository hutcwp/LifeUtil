package com.hutcwp.srw.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.contains
import androidx.fragment.app.FragmentActivity
import com.hutcwp.srw.MainGameActivity
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.controller.GameController
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/6 6:42 PM
 *  description :
 */
class MapView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var gameController: GameController? = null

    var activity: FragmentActivity? = null

    var controllerMenuDialog: ControllerMenuDialog? = null


    var mapSpriteSpriteList: List<MapSprite> = mutableListOf()
    var robotSpriteList: List<RobotSprite> = mutableListOf()

    var mapWidth = 0
    var mapHeight = 0

    fun initMap(mapSpriteSpriteList: List<MapSprite>) {
        this.mapSpriteSpriteList = mapSpriteSpriteList
        mapSpriteSpriteList.forEach {
            addViewToMap(it)
            mapWidth = Math.max(mapWidth, it.pos.x)
            mapHeight = Math.max(mapHeight, it.pos.y)
        }
    }

    fun initRobots(robotSpriteList: List<RobotSprite>) {
        this.robotSpriteList = robotSpriteList
        addRobotList(robotSpriteList)
    }

    fun initSelect(selectSprite: SelectSprite) {
        addViewToMap(selectSprite)
    }

    private fun addRobotList(robotSpriteList: List<RobotSprite>) {
        robotSpriteList.forEach {
            addViewToMap(it)
        }
    }

    private fun addViewToMap(sprite: BaseSprite) {
        val view = sprite.view
        assert(sprite.view != null)

        val x = sprite.pos.x
        val y = sprite.pos.y
        val marginStart = x * MainGameActivity.UNIT_MAP
        val marginTop = y * MainGameActivity.UNIT_MAP

        val lp = FrameLayout.LayoutParams(MainGameActivity.UNIT_MAP, MainGameActivity.UNIT_MAP).apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.marginStart = marginStart
            }
            this.topMargin = marginTop
        }

        view.layoutParams = lp

        view.setOnClickListener { gameController?.select(sprite) }

        addView(view)
    }

    fun posInMapRange(pos: Pos): Boolean {
        return (pos.x in 0 until mapWidth) && (pos.y in 0 until mapHeight)
    }

    fun posHasRobot(pos: Pos): Boolean {
        return robotSpriteList.find { it.pos == pos } != null
    }

    fun dismissMenu() {
        controllerMenuDialog?.dismissAllowingStateLoss()
    }

    /**
     * 展示控制菜单
     */
    fun showControllerMenuDialog(sprite: RobotSprite) {
        if (controllerMenuDialog == null) {
            controllerMenuDialog = ControllerMenuDialog.newInstance(sprite.robot)
            controllerMenuDialog?.iControllerMenu = gameController
        }
        controllerMenuDialog?.updateRobot(sprite.robot)
        ControllerMenuDialog.showMenu(controllerMenuDialog!!, activity!!.supportFragmentManager)
    }

    fun updateViewPos(sprite: BaseSprite?) {
        sprite ?: return
        if (this.contains(sprite.view)) {

            val x = sprite.pos.x
            val y = sprite.pos.y
            val marginStart = x * MainGameActivity.UNIT_MAP
            val marginTop = y * MainGameActivity.UNIT_MAP
            Log.d("hutcwp", "更新到pos" + sprite.pos.toString())

            val lp = FrameLayout.LayoutParams(MainGameActivity.UNIT_MAP, MainGameActivity.UNIT_MAP).apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    this.marginStart = marginStart
                }
                this.topMargin = marginTop
            }

            sprite.view.layoutParams = lp
            sprite.view.invalidate()
        }
    }

    fun updatePosWithAnim(sprite: BaseSprite, oldPos: Pos, newPos: Pos) {
        val durX = (newPos.x - oldPos.x) * sprite.view.width.toFloat()
        val durY = (newPos.y - oldPos.y) * sprite.view.height.toFloat()

        val objectAnimatorX = ObjectAnimator.ofFloat(sprite.view, "translationX", 0f, durX)
        val objectAnimatorY = ObjectAnimator.ofFloat(sprite.view, "translationY", 0f, durY)
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(objectAnimatorX,objectAnimatorY)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                sprite.view.translationX = 0f
                sprite.view.translationY = 0f
                sprite.pos = newPos
                updateViewPos(sprite)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
        animatorSet.start();
    }


    /**
     *  移动状态下，找地图可移动的mapSprite
     */
    fun canMove(pos: Pos): Boolean {
        return mapSpriteSpriteList.find { it.pos == pos && it.isEnable() } != null
    }

    fun showMoveRange(pos: Pos, range: Int) {
        mapSpriteSpriteList.forEach {
            val absX = Math.abs(it.pos.x - pos.x)
            val absY = Math.abs(it.pos.y - pos.y)
            val inMoveRange = (absX + absY <= range)
            //移动范围内，地图内，没有机器人
            if (inMoveRange && posInMapRange(it.pos) && posHasRobot(it.pos).not()) {
                it.showEnable()
                Log.i("test", "show enable x=${it.pos.x} , y=${it.pos.y}")
            } else {
                it.showUnable()
            }
        }
    }

    fun showAttackRange(pos: Pos, range: Int) {
        mapSpriteSpriteList.forEach {
            val absX = Math.abs(it.pos.x - pos.x)
            val absY = Math.abs(it.pos.y - pos.y)
            val inAttackRange = (absX + absY <= range)
            //是否在攻击范围内
            if (inAttackRange) {
                it.showEnable()
            } else {
                it.showUnable()
            }
        }
    }

    fun resetNormalMap() {
        mapSpriteSpriteList.forEach {
            it.showEnable()
        }
    }


    fun removeRobotSprite(robotSprite: RobotSprite) {
        for (i in 0..childCount) {
            if (robotSprite.view == getChildAt(i)) {
                removeView(robotSprite.view)
                return
            }
        }
    }


}