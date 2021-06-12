package club.hutcwp.lifeutil.ui.home.top

import androidx.viewpager.widget.ViewPager
import hut.cwp.core.MvpView

/**
 * Created by hutcwp on 2018/10/13 01:39
 *
 *
 * 一级tab接口
 */
interface ITop<T> : MvpView {
    fun setUpViewPager(viewPager: ViewPager, categories: List<T>)
    fun initTabLayout(categories: List<T>)
}
