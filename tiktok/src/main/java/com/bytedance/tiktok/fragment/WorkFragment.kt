package com.bytedance.tiktok.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

import com.bytedance.tiktok.adapter.WorkAdapter
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.bean.DataCreate
import com.example.tiktok.R
import kotlinx.android.synthetic.main.fragment_work.*


/**
 * create by libo
 * create on 2020-05-19
 * description 个人作品fragment
 */
class WorkFragment : BaseFragment() {
    private var workAdapter: WorkAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_work
    }

    override fun initView(rootView: View) {

    }

    override fun init() {
        recyclerview!!.layoutManager = GridLayoutManager(activity, 3)
        workAdapter = WorkAdapter(activity, DataCreate.datas)
        recyclerview!!.adapter = workAdapter
    }
}