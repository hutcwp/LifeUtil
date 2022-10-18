package com.hutcwp.live

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 *  author : kevin
 *  date : 2021/12/12 3:06 PM
 *  description :
 */
class TestFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.live_layout_test,container,false)
    }
}