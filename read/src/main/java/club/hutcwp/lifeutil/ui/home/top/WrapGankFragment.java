package club.hutcwp.lifeutil.ui.home.top;

import org.jetbrains.annotations.NotNull;

import hut.cwp.annotations.mvp.DelegateBind;

/**
 * @ProjectName: LifeUtil$
 * @Package: club.hutcwp.lifeutil.ui.home.top$
 * @ClassName: WrapGankFragment$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 5:54 PM$
 */
@DelegateBind(presenter = WrapGankPresenter.class)
public class WrapGankFragment extends TopFragment {

    @NotNull
    @Override
    public String getTitle() {
        return "干货";
    }
}
