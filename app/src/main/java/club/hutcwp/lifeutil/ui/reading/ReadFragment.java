package club.hutcwp.lifeutil.ui.reading;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import java.util.List;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.adpter.ViewPagerAdapter;
import club.hutcwp.lifeutil.databinding.FragmentReadBinding;
import club.hutcwp.lifeutil.model.ReadCategory;
import club.hutcwp.lifeutil.ui.MainActivity;
import club.hutcwp.lifeutil.ui.base.BaseFragment;
import club.hutcwp.lifeutil.ui.base.IBaseView;
import hut.cwp.mvp.BindPresenter;

@BindPresenter(presenter = ReadPresenter.class)
public class ReadFragment extends BaseFragment<ReadPresenter, IBaseView> implements IBaseView<ReadCategory> {

    private FragmentReadBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initViews() {
        binding = (FragmentReadBinding) getBinding();
        binding.toolbar.setTitle("阅读");
        ((MainActivity) getActivity()).initDrawer(binding.toolbar);
    }

    protected void lazyFetchData() {
        getPresenter().getCategory();
    }

    /**
     * 初始化TabLayout
     *
     * @param readCategories 标签类
     */
    @Override
    public void initTabLayout(List<ReadCategory> readCategories) {
        setUpViewPager(binding.viewPager, readCategories);
        binding.viewPager.setOffscreenPageLimit(binding.viewPager.getAdapter().getCount());
        binding.tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        binding.tablayout.setupWithViewPager(binding.viewPager);
        binding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void setUpViewPager(ViewPager viewPager, List<ReadCategory> readCategories) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (ReadCategory category : readCategories) {
            Fragment fragment = new ReadCategoryFragment();
            Bundle data = new Bundle();
            data.putString("url", category.getUrl());
            fragment.setArguments(data);
            adapter.addFrag(fragment, category.getName());
        }
        viewPager.setAdapter(adapter);
    }
}
