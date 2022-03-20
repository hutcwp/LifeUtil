package com.hutcwp.srw.view

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.layout_controller_menu.*
import me.hutcwp.util.ResolutionUtils

/**
 *  author : kevin
 *  date : 2022/3/6 11:21 PM
 *  description : 控制菜单
 */
class ControllerMenuDialog(private var robot: Robot) : DialogFragment() {

    var iControllerMenu: IControllerMenu? = null

    fun updateRobot(robot: Robot) {
        this.robot = robot
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.apply {
                setDimAmount(0.0f)
                setBackgroundDrawableResource(R.color.transparent)
                addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                attributes.apply {
                    setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
                    dimAmount = 0.0f
                    width = ResolutionUtils.convertDpToPixel(400f, context).toInt()
                    height = ResolutionUtils.convertDpToPixel(200f, context).toInt()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_controller_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

        addViewToRightContainer(createRobotInfoView(robot))
    }

    private fun setListener() {
        btn_move?.setOnClickListener {
            iControllerMenu?.move()
        }
        btn_attack?.setOnClickListener {
            iControllerMenu?.attack()
        }
        btn_status?.setOnClickListener {
            iControllerMenu?.status()
        }
        btn_wait?.setOnClickListener {
            iControllerMenu?.finish()
        }
    }

    private fun addViewToRightContainer(view: View) {
        fl_container?.removeAllViews()
        fl_container?.addView(view)
    }

    private fun createRobotInfoView(robot: Robot): View {
        val robotView = RobotView(requireContext())
        robotView.updateData(robot)
        return robotView
    }


    companion object {

        const val TAG = "BattleSceneComponent"

        fun newInstance(robot: Robot): ControllerMenuDialog {
            return ControllerMenuDialog(robot)
        }

        fun showMenu(dialog: ControllerMenuDialog, fm: FragmentManager) {
            dialog.show(fm, ControllerMenuDialog.TAG)
        }
    }
}