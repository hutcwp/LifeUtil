package com.hutcwp.read.ui.home.tabs

import androidx.fragment.app.Fragment
import com.hutcwp.read.entitys.ReadCategory
import com.hutcwp.read.ui.home.impl.picture.PictureFragment
import hut.cwp.annotations.mvp.DelegateBind

/**
 * @ClassName: WrapPhotoFragment$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/16$ 7:13 PM$
 */
@DelegateBind(presenter = WrapPhotoPresenter::class)
class WrapPhotoFragment : TopFragment() {

    override val title: String get() = "福利"

    override fun getFragment(category: ReadCategory): Fragment {
        return PictureFragment.instance(category.url)
    }
}