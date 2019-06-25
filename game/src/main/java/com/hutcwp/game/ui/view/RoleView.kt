package com.hutcwp.game.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.game.R

/**
 *
 * Created by hutcwp on 2019-06-25 17:40
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class RoleView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) ,View.OnClickListener{


    constructor(context: Context?) : this(context, null)

    init {
        inflate(context, R.layout.game_view_role, this@RoleView)
        initView()
        setListener()
        initData()
    }

    private fun setListener() {
        this@RoleView.setOnClickListener(this)
    }

    private fun initData() {

    }

    private fun initView() {

    }

    override fun onClick(view: View?) {

    }
}