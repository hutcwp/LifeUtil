package club.hutcwp.lifeutil.ui.home.top;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import club.hutcwp.lifeutil.ui.home.sub.picture.PictureFragment;
import hut.cwp.mvp.BindPresenter;

/**
 * @ProjectName: LifeUtil$
 * @Package: club.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapPhotoFragment$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 7:13 PM$
 */
@BindPresenter(presenter = WrapPhotoPresenter.class)
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
