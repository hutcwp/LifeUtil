package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
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


    fun initMap(mapSpriteSpriteList: List<MapSprite>) {
        this.mapSpriteSpriteList = mapSpriteSpriteList
        mapSpriteSpriteList.forEach {
            addViewToMap(it)
        }
    }

    fun initRobots(robotSpriteList: MutableList<RobotSprite>) {
        addRobotList(robotSpriteList.toList())
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
        val marginStart = y * MainGameActivity.UNIT_MAP
        val marginTop = x * MainGameActivity.UNIT_MAP

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


    fun dismissMenu() {
        controllerMenuDialog?.dismissAllowingStateLoss()
    }

    fun selectSprite(sprite: BaseSprite, isMove: Boolean) {
        if (!isMove) {
            val pos = sprite.pos
            gameController?.updateSelectSpritePos(pos)
            when (sprite) {
                is MapSprite -> {
                    dismissMenu()
                }
                is RobotSprite -> {
                    if (controllerMenuDialog == null) {
                        controllerMenuDialog = ControllerMenuDialog.newInstance(sprite.robot)
                        controllerMenuDialog?.iControllerMenu = gameController
                    }
                    controllerMenuDialog?.updateRobot(sprite.robot)
                    ControllerMenuDialog.showMenu(controllerMenuDialog!!, activity!!.supportFragmentManager)
                }
            }
        } else {
            if (sprite is MapSprite) {
                if (sprite.view.alpha == 0.5f) {

                }
            }
        }
    }

    fun updateViewPos(sprite: BaseSprite?) {
        sprite ?: return
        if (this.contains(sprite.view)) {
            val x = sprite.pos.x
            val y = sprite.pos.y
            val marginStart = y * MainGameActivity.UNIT_MAP
            val marginTop = x * MainGameActivity.UNIT_MAP

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

    fun showMoveRange(pos: Pos, range: Int) {
        mapSpriteSpriteList.forEach {
            val absX = Math.abs(it.pos.x - pos.x)
            val absY = Math.abs(it.pos.y - pos.y)
            if (absX + absY < range) {
                it.view.alpha = 0.5f
            }
        }
    }

    fun showNormalRange() {
        mapSpriteSpriteList.forEach {
            it.view.alpha = 1f
        }
    }

    fun canMove(pos: Pos): Boolean {
        return mapSpriteSpriteList.find { it.pos == pos && it.view.alpha == 0.5f } != null
    }

    fun showRobotView(robot: Robot) {

    }


}