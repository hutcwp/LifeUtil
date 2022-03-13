package com.hutcwp.srw.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.activity_battle.*

/**
 *  author : kevin
 *  date : 2022/3/13 11:06 AM
 *  description :
 */
class BattleActivity : AppCompatActivity() {

    var attacker: Robot? = null
    var defender: Robot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        initData()
    }

    private fun initData() {
    }


}