package com.hutcwp.srw.bean

import android.content.Context
import com.hutcwp.srw.ui.BaseUI

/**
 *  author : kevin
 *  date : 2022/3/9 12:24 AM
 *  description :
 */
class SelectSprite(val context: Context, val params: RobotParams) : BaseSprite(BaseUI(context), params.pos,params.resId) {


}