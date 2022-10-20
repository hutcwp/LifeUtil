package club.hutcwp.lifeutil.ui.home.top

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.entitys.ReadCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.sub.news.NewsFragment
import com.example.annotations.mvp.DelegateBind
import com.google.android.material.tabs.TabLayout
import me.hutcwp.log.MLog

@DelegateBind(presenter = ReadPresenter::class)
class ReadFragment : BaseFragment<ReadPresenter, ReadFragment>(), IHome<ReadCategory> {

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_read
    }

    override fun initViews() {
        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "阅读"
        (activity as MainActivity).initDrawer(toolbar)
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
        MLog.info(TAG, "initTabLayout")
        val viewPager = rootView.findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = rootView.findViewById<TabLayout>(R.id.tablayout)
        setUpViewPager(viewPager, categories)
        viewPager.offscreenPageLimit = viewPager.adapter?.count ?: 0
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.white))
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setUpViewPager(viewPager: ViewPager, categories: List<ReadCategory>) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (category in categories) {
            val fragment = NewsFragment()
            val data = Bundle()
            data.putString("url", category.url)
            fragment.arguments = data
            adapter.addFrag(fragment, category.name!!)
        }
        viewPager.adapter = adapter
    }

    companion object {
        const val TAG = "ReadFragment"
    }
}
