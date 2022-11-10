package com.hutcwp.srw.ui.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.core.view.contains
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.ui.GameCamera
import com.hutcwp.srw.ui.view.anim.PointEvaluator
import com.hutcwp.srw.util.getRawPos
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/6 6:42 PM
 *  description :
 */
class MapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    var mapSpriteSpriteList: List<MapSprite> = mutableListOf()
    var robotSpriteList: List<RobotSprite> = mutableListOf()

    var mapWidth = 0
    var mapHeight = 0


    var mapUnit: Int = 60 //单位格子长度
        set(value) {
            field = value
            invalidate()
        }

    var gameCamera: GameCamera? = null


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    /**
     * 地图sprite
     */
    fun initMap(mapSpriteSpriteList: List<MapSprite>) {
        this.mapSpriteSpriteList = mapSpriteSpriteList
        mapSpriteSpriteList.forEach {
            addViewToMap(it)
            mapWidth = Math.max(mapWidth, it.pos.x.toInt())
            mapHeight = Math.max(mapHeight, it.pos.y.toInt())
        }
    }

    /**
     * 初始化机器人sprite
     */
    fun initRobots(robotSpriteList: List<RobotSprite>) {
        this.robotSpriteList = robotSpriteList
        addRobotList(robotSpriteList)
    }

    /**
     * 初始化选择器sprite
     */
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

        val x = sprite.pos.x
        val y = sprite.pos.y
        val marginStart = x * mapUnit
        val marginTop = y * mapUnit

        val lp = LayoutParams(mapUnit, mapUnit)
        view.layoutParams = lp.apply {
            this.marginStart = marginStart.toInt()
            this.topMargin = marginTop.toInt()
        }

        //todo 好像没用
//        view.setOnClickListener { gameController?.select(sprite) }

        addView(view)
    }

    fun posInMapRange(pos: Pos): Boolean {
        return (pos.x.toInt() in 0 until mapWidth) && (pos.y.toInt() in 0 until mapHeight)
    }

    fun posHasRobot(pos: Pos): Boolean {
        return robotSpriteList.find { it.pos == pos } != null
    }

    fun updateViewPos(sprite: BaseSprite?, pos: Pos) {
        sprite ?: return

        val view = sprite.view
        if (this.contains(view)) {
            sprite.pos = pos

            val x = sprite.pos.x
            val y = sprite.pos.y
            val marginStart = x * mapUnit
            val marginTop = y * mapUnit
            Log.d("hutcwp", "更新到pos" + sprite.pos.toString())

            val lp = view.layoutParams
            lp as FrameLayout.LayoutParams
            lp.apply {
                this.marginStart = marginStart.toInt()
                this.topMargin = marginTop.toInt()
            }

            view.layoutParams = lp
        }
    }

    fun updatePosWithAnim(sprite: BaseSprite, oldPos: Pos, newPos: Pos) {
        MLog.debug("hutcwp", "updatePosWithAnim: oldPos=$oldPos, newPos=$newPos")
        val durX = (newPos.x - oldPos.x) * sprite.view.width.toFloat()
        val durY = (newPos.y - oldPos.y) * sprite.view.height.toFloat()

        val objectAnimatorX = ObjectAnimator.ofFloat(
            sprite.view,
            "translationX",
            sprite.view.translationX,
            sprite.view.translationX + durX
        )
        val objectAnimatorY = ObjectAnimator.ofFloat(
            sprite.view, "translationY", sprite.view.translationY,
            sprite.view.translationY + durY
        )

//        val mapAnimatorX = ObjectAnimator.ofFloat(
//            this,
//            "translationX",
//            this.translationX,
//            this.translationX + durX
//        )
//        val mapAnimatorY = ObjectAnimator.ofFloat(
//            this, "translationY",
//            this.translationY,
//            this.translationY + durY
//        )


        val animatorSet = AnimatorSet()
//        animatorSet.playSequentially(objectAnimatorX, objectAnimatorY, mapAnimatorX, mapAnimatorY)
        animatorSet.play(objectAnimatorX)
//            .with(mapAnimatorX).with(mapAnimatorY)
            .before(objectAnimatorY)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                sprite.view.translationX = 0f
                sprite.view.translationY = 0f
                updateViewPos(sprite, newPos)

                gameCamera?.tryMove(-durX.toInt(), -durY.toInt())
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })

        animatorSet.duration = 300L
        animatorSet.start()
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

    /**
     * 平移
     */
    fun tryTranslate(dx: Int, dy: Int) {
        val mapAnimatorX = ObjectAnimator.ofFloat(
            this,
            "translationX",
            this.translationX,
            this.translationX + dx
        )
        val mapAnimatorY = ObjectAnimator.ofFloat(
            this, "translationY",
            this.translationY,
            this.translationY + dy
        )

        val animatorSet = AnimatorSet()
        animatorSet.play(mapAnimatorX).with(mapAnimatorY)
        animatorSet.duration = 300L
        animatorSet.start()
    }

    fun clearMap() {
        removeAllViews()
    }

}