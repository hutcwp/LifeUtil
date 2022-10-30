package com.hutcwp.framwork.component.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hutcwp.framwork.mvc.LayoutProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

/**
 * Description:
 *
 *
 * Created by n24314 on 2022/10/21. E-mail: caiwenpeng@corp.netease.com
 */
abstract class BaseViFragment<V : ViewDataBinding> : Fragment(), LayoutProvider {

    lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }
}