package com.hutcwp.read.ui.home.top;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import com.hutcwp.read.ui.home.sub.artical.ArticleFragment;
import hut.cwp.annotations.mvp.DelegateBind;

/**
 * @ProjectName: LifeUtil$
 * @Package: com.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapReadFragment$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 6:00 PM$
 */
@DelegateBind(presenter = WrapReadPresenter.class)
public class WrapReadFragment extends TopFragment {
    @NotNull
    @Override
    public String getTitle() {
        return "文章";
    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return new ArticleFragment();
    }
}
