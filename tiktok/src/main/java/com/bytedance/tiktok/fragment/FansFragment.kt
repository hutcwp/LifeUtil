package com.bytedance.tiktok.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bytedance.tiktok.adapter.FansAdapter
import com.bytedance.tiktok.base.BaseFragment
import com.bytedance.tiktok.bean.DataCreate
import com.example.tiktok.R


class FansFragment : BaseFragment() {
    private var fansAdapter: FansAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_fans
    }

    override fun initView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.recyclerView)
    }

    override fun init() {
        recyclerView?.layoutManager = LinearLayoutManager(context)
        fansAdapter = FansAdapter(context, DataCreate.userList)
        recyclerView?.adapter = fansAdapter
    }
}