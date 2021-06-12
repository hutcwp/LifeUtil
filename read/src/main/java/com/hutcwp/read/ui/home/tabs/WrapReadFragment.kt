package com.hutcwp.read.ui.home.tabs

import androidx.fragment.app.Fragment
import com.hutcwp.read.entitys.ReadCategory
import com.hutcwp.read.ui.home.impl.artical.ArticleFragment
import hut.cwp.annotations.mvp.DelegateBind

/**
 * @ClassName: WrapReadFragment$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/16$ 6:00 PM$
 */
@DelegateBind(presenter = WrapReadPresenter::class)
class WrapReadFragment : TopFragment() {
    override val title: String
        get() = "文章"

    override fun getFragment(category: ReadCategory): Fragment {
        return ArticleFragment.instance(category.url)
    }
}