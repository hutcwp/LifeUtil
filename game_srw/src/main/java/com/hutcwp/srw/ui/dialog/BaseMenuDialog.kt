package com.hutcwp.srw.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.hutcwp.srw.R
import me.hutcwp.util.ResolutionUtils

/**
 *  author : kevin
 *  date : 2022/11/11 01:18
 *  description :
 */
class BaseMenuDialog : DialogFragment() {

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


}