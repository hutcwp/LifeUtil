package com.bytedance.tiktok.activity


import com.bytedance.tiktok.base.BaseActivity
import com.bytedance.tiktok.fragment.RecommendFragment
import com.example.tiktok.R

/**
 * create by libo
 * create on 2020-05-24
 * description 视频全屏播放页
 */
class PlayListActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_play_list
    }

    override fun initData() {
        supportFragmentManager.beginTransaction().add(R.id.framelayout, RecommendFragment()).commit()
    }

    override fun initView() {

    }

    companion object {
        @JvmField
        var initPos = 0
    }
}