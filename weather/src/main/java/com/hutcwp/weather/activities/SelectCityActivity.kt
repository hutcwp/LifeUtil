/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package com.hutcwp.weather.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.weather.fragment.AreaSelectListFragment
import com.hutcwp.weather.R

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */
@Route(path = "/weather/select")
class SelectCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wea_activity_select_city)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            //活动布局显示在状态栏上面
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //将状态栏设置为透明
            window.statusBarColor = Color.TRANSPARENT
        }

        val fragment = AreaSelectListFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.commitAllowingStateLoss()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val cityCode = pref.getString("weather_id", null)

        if (cityCode != null) {
            startActivity(Intent(this, WeatherInfoActivity::class.java))
            finish()
        }
    }
}
