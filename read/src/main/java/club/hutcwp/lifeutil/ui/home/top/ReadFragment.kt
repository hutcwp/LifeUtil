package club.hutcwp.lifeutil.ui.home.top

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.sub.news.NewsFragment
import hut.cwp.mvp.BindPresenter
import kotlinx.android.synthetic.main.read_fragment_read.*
import me.hutcwp.log.MLog

@BindPresenter(presenter = ReadPresenter::class)
class ReadFragment : BaseFragment<ReadPresenter, ReadFragment>(), IHome<ReadCategory> {


    override fun getLayoutId(): Int {
        return R.layout.read_fragment_read
    }

    override fun initViews() {
        rootView.findViewById<Toolbar>(R.id.toolbar).title = "阅读"
        (activity as MainActivity).initDrawer(rootView.findViewById<Toolbar>(R.id.toolbar))
    }

    override fun lazyFetchData() {
        presenter.getCategoryV2()
    }

    /**
     * 初始化TabLayout
     *
     * @param categories 标签类
     */
    override fun initTabLayout(categories: List<ReadCategory>) {
        MLog.info("cwp", "initTabLayout")
        setUpViewPager(viewPager, categories)
        viewPager.offscreenPageLimit = viewPager.adapter?.count ?: 0
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.white))
        tablayout.setupWithViewPager(viewPager)
        tablayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setUpViewPager(viewPager: ViewPager, readCategories: List<ReadCategory>) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (category in readCategories) {
            val fragment = NewsFragment()
            val data = Bundle()
            data.putString("url", category.url)
            fragment.arguments = data
            adapter.addFrag(fragment, category.name!!)
        }
        viewPager.adapter = adapter
    }
}
