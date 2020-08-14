package club.hutcwp.lifeutil.ui.home.top


import android.os.Bundle
import androidx.appcompat.widget.Toolbar

import com.google.android.material.tabs.TabLayout

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.entitys.PhotoCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.sub.picture.PictureFragment
import hut.cwp.mvp.BindPresenter
import kotlinx.android.synthetic.main.read_fragment_girl.*

@BindPresenter(presenter = PhotoPresenter::class)
class PhotoFragment : BaseFragment<PhotoPresenter, PhotoFragment>(), IHome<PhotoCategory> {

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_girl
    }

    override fun initViews() {
        rootView.findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.gank)
        (activity as MainActivity).initDrawer(rootView.findViewById<Toolbar>(R.id.toolbar))
    }

    override fun lazyFetchData() {
        presenter.getCategory()
    }

    override fun initTabLayout(categories: List<PhotoCategory>) {
        setUpViewPager(rootView.findViewById(R.id.viewPager), categories)
        viewPager.offscreenPageLimit = viewPager.adapter?.count ?: 0
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.white))
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setUpViewPager(viewPager: ViewPager, categories: List<PhotoCategory>) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (category in categories) {
            val categoryFragment = PictureFragment()
            val data = Bundle()
            data.putInt("type", category.type)
            data.putString("url", category.url)
            categoryFragment.arguments = data
            adapter.addFrag(categoryFragment, category.name!!)
        }
        viewPager.adapter = adapter
    }
}
