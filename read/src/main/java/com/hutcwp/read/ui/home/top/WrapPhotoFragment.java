package com.hutcwp.read.ui.home.top;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import com.hutcwp.read.ui.home.sub.picture.PictureFragment;
import hut.cwp.annotations.mvp.DelegateBind;

/**
 * @ProjectName: LifeUtil$
 * @Package: com.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapPhotoFragment$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 7:13 PM$
 */
@DelegateBind(presenter = WrapPhotoPresenter.class)
public class WrapPhotoFragment extends TopFragment {

    @NotNull
    @Override
    public String getTitle() {
        return "福利";
    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return new PictureFragment();
    }
}
