package com.hutcwp.framwork.mvc;

import androidx.annotation.LayoutRes;

/**
 * 提供 layoutId 给 databinding 的接口
 */
public interface LayoutProvider {

    @LayoutRes
    int getLayoutId();

}
