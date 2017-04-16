package club.hutcwp.lifeutil.ui.girl;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter;
import club.hutcwp.lifeutil.databinding.FragmentGirlBinding;
import club.hutcwp.lifeutil.model.PhotoCategory;
import club.hutcwp.lifeutil.ui.MainActivity;
import club.hutcwp.lifeutil.ui.base.BaseFragment;

public class PhotoFragment extends BaseFragment {

    FragmentGirlBinding binding;
    List<PhotoCategory> photoCategories= new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl;
    }


    @Override
    protected void initViews() {

        binding = (FragmentGirlBinding) getBinding();

        binding.toolbar.setTitle(getString(R.string.gank));
        ((MainActivity) getActivity()).initDrawer(binding.toolbar);
        initCategorys();
    }


    @Override
    protected void lazyFetchData() {

       initTabLayout(photoCategories);

    }

    /**
     * 初始化TabLayout
     */
    public void initTabLayout(List<PhotoCategory> photoCategories) {

        setUpViewPager(binding.viewPager, photoCategories);
        binding.viewPager.setOffscreenPageLimit(binding.viewPager.getAdapter().getCount());
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setUpViewPager(ViewPager viewPager, List<PhotoCategory> photoCategories) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fragment = new GankGirlFragment();
        adapter.addFrag(fragment, "靓女专题");

        for (PhotoCategory category : photoCategories) {
            Fragment categoryFragment = new PhotoCategoryFragment();
            Bundle data = new Bundle();
            data.putString("url", category.getUrl());
            categoryFragment.setArguments(data);
            adapter.addFrag(categoryFragment, category.getName());
        }

        viewPager.setAdapter(adapter);

    }

    public void initCategorys(){

        photoCategories.add(new PhotoCategory("摄影世界","http://www.egouz.com/pics/icon/"));
        photoCategories.add(new PhotoCategory("插画设计","http://www.egouz.com/pics/vector/"));
        photoCategories.add(new PhotoCategory("桌面壁纸","http://www.egouz.com/pics/wallpaper/"));
        photoCategories.add(new PhotoCategory("艺术人生","http://www.egouz.com/pics/pattern/"));

    }


}
