package com.bytedance.tiktok.activity

import android.view.View
import android.widget.ImageView

import com.bytedance.tiktok.base.BaseActivity
import com.example.tiktok.R

class ShowImageActivity : BaseActivity() {

    var ivHead: ImageView? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_show_image
    }

    override fun initData() {
        ivHead!!.setOnClickListener { v: View? -> finish() }
        val headRes = intent.getIntExtra("res", 0)
        ivHead!!.setImageResource(headRes)
    }

    override fun initView() {
       ivHead = findViewById(R.id.iv_head)
    }
}