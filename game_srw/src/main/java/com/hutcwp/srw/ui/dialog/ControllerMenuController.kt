package com.hutcwp.srw.ui.dialog

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.controller.IGameController
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.ui.activity.MainGameActivity
import com.hutcwp.srw.ui.view.IControllerMenu
import com.hutcwp.srw.ui.view.RobotView
import java.util.LinkedList

/**
 *  author : kevin
 *  date : 2022/3/6 11:21 PM
 *  description : 控制菜单
 */
class ControllerMenuController(private val rootView: View, private val iSceneSwitch: ISceneSwitch) :
    IGameController {

    var iControllerMenu: IControllerMenu? = null

    private var selectItem: Button? = null
    private var robotSprite: RobotSprite? = null


    private val itemList: LinkedList<Button> = LinkedList<Button>()


    private var btnMove: Button? = null
    private var btnAttack: Button? = null
    private var btnStatus: Button? = null
    private var btnSkill: Button? = null
    private var btnWait: Button? = null

    private var flContainer: FrameLayout? = null

    init {
        btnMove = rootView.findViewById(R.id.btn_move)
        btnAttack = rootView.findViewById(R.id.btn_attack)
        btnStatus = rootView.findViewById(R.id.btn_status)
        btnSkill = rootView.findViewById(R.id.btn_skill)
        btnWait = rootView.findViewById(R.id.btn_wait)

        flContainer = rootView.findViewById(R.id.fl_container)

        itemList.addLast(btnMove)
        itemList.addLast(btnAttack)
        itemList.addLast(btnStatus)
        itemList.addLast(btnSkill)
        itemList.addLast(btnWait)

        updateSelectItem(itemList.first)

        setListener()
    }

    fun showDialog(robotSprite: RobotSprite) {
        iSceneSwitch.setGameController(this)

        rootView.visibility = View.VISIBLE

        this.robotSprite = robotSprite

        btnMove?.isVisible = robotSprite.canMove
        addViewToRightContainer(createRobotInfoView(robotSprite.robot))
    }

    fun dismissDialog() {
        rootView.visibility = View.GONE
        iSceneSwitch.removeGameController(this)
    }

    private fun setListener() {
//        btnMove?.setOnClickListener {
//            iControllerMenu?.move()
//        }
//        btnAttack?.setOnClickListener {
//            iControllerMenu?.attack()
//        }
//        btnStatus?.setOnClickListener {
//            iControllerMenu?.status()
//        }
//        btnWait?.setOnClickListener {
//            iControllerMenu?.finish()
//        }
    }

    private fun addViewToRightContainer(view: View) {
        flContainer?.removeAllViews()
        flContainer?.addView(view)
    }

    private fun createRobotInfoView(robot: Robot): View {
        val robotView = RobotView(rootView.context)
        robotView.updateData(robot)
        return robotView
    }

    override fun up() {
        val index = itemList.indexOf(selectItem)

        if (index != -1) {
            val selectIndex = if (index - 1 < 0) {
                itemList.lastIndex
            } else {
                index - 1
            }
            updateSelectItem(itemList[selectIndex])
        }
    }

    override fun down() {
        val index = itemList.indexOf(selectItem)

        if (index != -1) {
            val selectIndex = if (index + 1 > itemList.lastIndex) {
                0
            } else {
                index + 1
            }
            updateSelectItem(itemList[selectIndex])
        }
    }

    override fun left() {

    }

    override fun right() {

    }

    override fun ok() {
        when (selectItem) {
            btnMove -> {
                iControllerMenu?.move()
            }
            btnAttack -> {
                iControllerMenu?.attack()
            }
            btnStatus -> {
                iControllerMenu?.status()
            }
            btnSkill -> {
            }
            btnWait -> {
                iControllerMenu?.finish()
            }
            else -> {

            }
        }
    }

    override fun cancel() {
        dismissDialog()
    }

    private fun updateSelectItem(button: Button) {
        selectItem = button

        itemList.forEach {
            it.isEnabled = button == it
        }
    }


    companion object {
        const val TAG = "BattleSceneComponent"
    }

}