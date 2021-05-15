package com.bytedance.tiktok.activity

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.androidkun.xtablayout.XTabLayout

import com.bytedance.tiktok.base.BaseActivity
import com.bytedance.tiktok.base.CommPagerAdapter
import com.bytedance.tiktok.fragment.FansFragment
import com.example.tiktok.R
import kotlinx.android.synthetic.main.activity_focus.*
import java.util.*

/**
 * create by libo
 * create on 2020-05-14
 * description 粉丝关注人页面
 */
class FocusActivity : BaseActivity() {



    var viewPager: ViewPager? = null

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null
    private val titles = arrayOf("关注 128", "粉丝 128", "推荐关注")

    override fun setLayoutId(): Int {
        return R.layout.activity_focus
    }

    override fun initData() {
        for (i in titles.indices) {
            fragments.add(FansFragment())
            tablayout!!.addTab(tablayout!!.newTab().setText(titles[i]))
        }
        pagerAdapter = CommPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager!!.adapter = pagerAdapter
        tablayout!!.setupWithViewPager(viewPager)
    }

    override fun initView() {
        viewPager = findViewById(R.id.viewpager);
    }
}