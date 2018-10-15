package club.hutcwp.lifeutil.ui.home.top;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import java.util.List;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter;
import club.hutcwp.lifeutil.databinding.FragmentGirlBinding;
import club.hutcwp.lifeutil.entitys.PhotoCategory;
import club.hutcwp.lifeutil.ui.MainActivity;
import club.hutcwp.lifeutil.ui.base.BaseFragment;
import club.hutcwp.lifeutil.ui.home.sub.picture.PictureFragment;
import hut.cwp.mvp.BindPresenter;

@BindPresenter(presenter =  PhotoPresenter.class)
public class PhotoFragment extends BaseFragment<PhotoPresenter,IHome> implements IHome<PhotoCategory> {

    private FragmentGirlBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl;
    }


    @Override
    protected void initViews() {
        binding = (FragmentGirlBinding) getBinding();
        binding.toolbar.setTitle(getString(R.string.gank));
        ((MainActivity) getActivity()).initDrawer(binding.toolbar);
    }


    @Override
    protected void lazyFetchData() {
        getPresenter().getCategory();
    }

    /**
     * 初始化TabLayout
     */
    @Override
    public void initTabLayout(List<PhotoCategory> photoCategories) {
        setUpViewPager(binding.viewPager, photoCategories);
        binding.viewPager.setOffscreenPageLimit(binding.viewPager.getAdapter().getCount());
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
    }

    @Override
    public void setUpViewPager(ViewPager viewPager, List<PhotoCategory> categories) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (PhotoCategory category : categories) {
            Fragment categoryFragment = new PictureFragment();
            Bundle data = new Bundle();
            data.putInt("type",category.getType());
            data.putString("url", category.getUrl());
            categoryFragment.setArguments(data);
            adapter.addFrag(categoryFragment, category.getName());
        }
        viewPager.setAdapter(adapter);
    }
}
