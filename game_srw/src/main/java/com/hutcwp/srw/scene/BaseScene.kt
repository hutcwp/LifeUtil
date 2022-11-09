package com.hutcwp.srw.scene

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 *  author : kevin
 *  date : 2022/4/9 6:34 PM
 *  description : 场景
 *  场景是复用的
 *  initView() 只在第一次初始化的时候才走
 *  initData() 初始化数据
 */
abstract class BaseScene(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), IScene {

    var rootView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.rootView = view
        initWithContext(true)
    }

    final override fun initWithContext(initFirst: Boolean) {
        if (initFirst) {
            firstInitView(rootView!!)
        }
        initData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        release()
    }

    override fun release() {
    }

    abstract fun initData()

    /**
     * 初始化View，仅仅执行一次而已
     */
    abstract fun firstInitView(rootView: View)


}