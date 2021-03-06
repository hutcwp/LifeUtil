package club.hutcwp.lifeutil.ui.home.top

import androidx.viewpager.widget.ViewPager

import hut.cwp.mvp.MvpView

/**
 * Created by hutcwp on 2018/10/13 01:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 一级tab接口
 */
interface IHome<T> : MvpView {
    fun setUpViewPager(viewPager: ViewPager, categories: List<T>)
    fun initTabLayout(categories: List<T>)
}
