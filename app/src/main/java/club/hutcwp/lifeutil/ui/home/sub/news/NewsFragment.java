package club.hutcwp.lifeutil.ui.home.sub.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.adpter.ReadAdapter;
import club.hutcwp.lifeutil.databinding.FragmentCategoryBinding;
import club.hutcwp.lifeutil.model.ReadItem;
import club.hutcwp.lifeutil.ui.MainActivity;
import club.hutcwp.lifeutil.ui.base.BaseFragment;
import hut.cwp.mvp.BindPresenter;

/**
 * 阅读的子类
 */
@BindPresenter(presenter = NewsPresenter.class)
//public class NewsFragment extends BaseFragment implements ICategory{
public class NewsFragment extends BaseFragment<NewsPresenter,ICategory> implements ICategory{

    private ReadAdapter adapter;

    private FragmentCategoryBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initViews() {
        binding = (FragmentCategoryBinding) getBinding();
        adapter = new ReadAdapter(getContext(), null);
        setting();
    }

    @Override
    protected void lazyFetchData() {
        getPresenter().getDataFromServer();
    }

    /**
     * 设置监听等
     */
    public void setting() {
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getDataFromServer();
            }
        });
    }

    @Override
    public void showSnack(String msg) {
        ((MainActivity) getActivity()).showSnack(msg);
    }

    @Override
    public void setRefreshing(boolean status) {
        binding.swipeRefreshLayout.setRefreshing(status);
    }

    @Override
    public void setNewData(List<ReadItem> data) {
        adapter.setNewData(data);
    }

    @Override
    public void addNewData(int pos,List<ReadItem> data) {
        adapter.addData(pos,data);
    }

    public List<ReadItem> getData(){
        return adapter.getData();
    }

}
