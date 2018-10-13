package club.hutcwp.lifeutil.ui.home.top;

import android.support.v4.view.ViewPager;

import java.util.List;

import club.hutcwp.lifeutil.model.ReadCategory;
import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2018/10/13 01:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 一级tab接口
 **/
public interface IHome<T> extends MvpView{

    void setUpViewPager(ViewPager viewPager, List<T> categories);

    void initTabLayout(List<T> categories);

}
