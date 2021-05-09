package club.hutcwp.lifeutil.ui.home.top

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.ui.home.sub.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.top_fragment_read.*
import me.hutcwp.log.MLog

@DelegateBind(presenter = TopPresenter::class)
open class TopFragment : BaseFragment<TopPresenter, TopFragment>(), ITop<ReadCategory> {

    open val title = "title"

    override fun getLayoutId(): Int {
        return R.layout.top_fragment_read
    }

    override fun initViews() {
        rootView.findViewById<Toolbar>(R.id.toolbar).title = title
        (activity as MainActivity).initDrawer(rootView.findViewById(R.id.toolbar))
    }

    override fun lazyFetchData() {
        presenter.getCategoryV2()
    }

    override fun initTabLayout(categories: List<ReadCategory>) {
        MLog.info("TopFragment", "initTabLayout")
        setUpViewPager(viewPager, categories)
        viewPager.offscreenPageLimit = viewPager.adapter?.count ?: 0
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.white))
        tablayout.setupWithViewPager(viewPager)
        tablayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setUpViewPager(viewPager: ViewPager, readCategories: List<ReadCategory>) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (category in readCategories) {
            val fragment = getFragment()
            val data = Bundle()
            data.putString("url", category.url)
            fragment.arguments = data
            adapter.addFrag(fragment, category.name!!)
        }
        viewPager.adapter = adapter
    }

    open fun getFragment(): Fragment {
        return NewsFragment()
    }
}
