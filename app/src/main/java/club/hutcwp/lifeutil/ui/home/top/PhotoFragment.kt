package club.hutcwp.lifeutil.ui.home.top


import android.os.Bundle

import com.google.android.material.tabs.TabLayout

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter
import club.hutcwp.lifeutil.databinding.FragmentGirlBinding
import club.hutcwp.lifeutil.entitys.PhotoCategory
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.sub.picture.PictureFragment
import hut.cwp.mvp.BindPresenter

@BindPresenter(presenter = PhotoPresenter::class)
class PhotoFragment : BaseFragment<PhotoPresenter, PhotoFragment>(), IHome<PhotoCategory> {

    private var fragmentGirlBinding: FragmentGirlBinding? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_girl
    }


    override fun initViews() {
        fragmentGirlBinding = getBinding() as FragmentGirlBinding
        fragmentGirlBinding!!.toolbar.title = getString(R.string.gank)
        (activity as MainActivity).initDrawer(fragmentGirlBinding!!.toolbar)
    }


    override fun lazyFetchData() {
        presenter.getCategory()
    }

    /**
     * 初始化TabLayout
     */
    override fun initTabLayout(categories: List<PhotoCategory>) {
        setUpViewPager(fragmentGirlBinding!!.viewPager, categories)
        fragmentGirlBinding!!.viewPager.offscreenPageLimit = fragmentGirlBinding!!.viewPager.adapter!!.count
        fragmentGirlBinding!!.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.white))
        fragmentGirlBinding!!.tabLayout.setupWithViewPager(fragmentGirlBinding!!.viewPager)
        fragmentGirlBinding!!.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setUpViewPager(viewPager: ViewPager, categories: List<PhotoCategory>) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        for (category in categories) {
            val categoryFragment = PictureFragment()
            val data = Bundle()
            data.putInt("type", category.type)
            data.putString("url", category.url)
            categoryFragment.arguments = data
            adapter.addFrag(categoryFragment, category.name)
        }
        viewPager.adapter = adapter
    }
}
