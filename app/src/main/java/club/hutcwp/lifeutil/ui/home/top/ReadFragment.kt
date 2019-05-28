package club.hutcwp.lifeutil.ui.home.top

import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.databinding.FragmentReadBinding
import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.sub.news.NewsFragment
import hut.cwp.mvp.BindPresenter

@BindPresenter(presenter = ReadPresenter::class)
class ReadFragment : BaseFragment<ReadPresenter, ReadFragment>(), IHome<ReadCategory> {

    private var fragmentReadBinding: FragmentReadBinding? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_read
    }

    override fun initViews() {
        fragmentReadBinding = binding as FragmentReadBinding
        fragmentReadBinding!!.toolbar.title = "阅读"
        (activity as MainActivity).initDrawer(fragmentReadBinding!!.toolbar)
    }

    override fun lazyFetchData() {
        presenter.getCategory()
    }

    /**
     * 初始化TabLayout
     *
     * @param categories 标签类
     */
    override fun initTabLayout(categories: List<ReadCategory>) {
        Log.i("cwp","initTabLayout")
        setUpViewPager(fragmentReadBinding!!.viewPager, categories)
        fragmentReadBinding!!.viewPager.offscreenPageLimit = fragmentReadBinding!!.viewPager.adapter!!.count
        fragmentReadBinding!!.tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.white))
        fragmentReadBinding!!.tablayout.setupWithViewPager(fragmentReadBinding!!.viewPager)
        fragmentReadBinding!!.tablayout.tabMode = TabLayout.MODE_SCROLLABLE
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
