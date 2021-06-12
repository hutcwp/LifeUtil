package com.hutcwp.read.ui.home.tabs

import androidx.fragment.app.Fragment
import com.hutcwp.read.entitys.ReadCategory
import com.hutcwp.read.ui.home.impl.news.NewsFragment
import hut.cwp.annotations.mvp.DelegateBind

/**
 * @ClassName: WrapGankFragment$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/16$ 5:54 PM$
 */
@DelegateBind(presenter = WrapGankPresenter::class)
class WrapGankFragment : TopFragment() {

    override val title: String
        get() = "干货"

    override fun getFragment(category: ReadCategory): Fragment {
        return NewsFragment.instance(category.url)
    }
}