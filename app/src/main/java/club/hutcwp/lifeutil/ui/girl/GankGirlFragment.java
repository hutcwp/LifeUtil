package club.hutcwp.lifeutil.ui.girl;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Random;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.adpter.GankGirlAdapter;
import club.hutcwp.lifeutil.databinding.FragmentGankGirlBinding;
import club.hutcwp.lifeutil.http.ApiFactory;
import club.hutcwp.lifeutil.http.BaseGankResponse;
import club.hutcwp.lifeutil.model.Girl;
import club.hutcwp.lifeutil.ui.MainActivity;
import club.hutcwp.lifeutil.ui.base.BaseFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GankGirlFragment extends BaseFragment {


    private GankGirlAdapter adapter;

    private static int curPage = 1;


    private FragmentGankGirlBinding binding;


    private static boolean isRefresh = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_girl;
    }

    @Override
    protected void lazyFetchData() {
        getGank(curPage);
    }

    @Override
    protected void initViews() {
        binding = (FragmentGankGirlBinding) getBinding();
        //可能会出现空指针异常
        adapter = new GankGirlAdapter(getContext(), null);
        binding.gridRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.gridRecycler.addItemDecoration(new SpacesItemDecoration(14));
        binding.gridRecycler.setAdapter(adapter);

        setting();
    }

    /**
     * 注意，因放置在initView方法的最后，以避免出现空指针
     */
    public void setting() {

        binding.swipRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        binding.swipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                curPage = 1;
                getGank(curPage);
            }
        });

        binding.gridRecycler.addOnScrollListener(new RecyclerView.OnScrollListener()
         {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!binding.gridRecycler.canScrollVertically(1)) {
                    isRefresh = false;
                    getGank(++curPage);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    /**
     * 获取数据
     *
     * @param curPage 当前页
     */
    public void getGank(int curPage) {

        ApiFactory.getGirlsController().getGank(curPage + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseGankResponse<List<Girl>>>() {
                    @Override
                    public void onCompleted() {
                        ((MainActivity) getActivity()).showSnack("加载完成");
                        binding.swipRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("teste", e.getMessage());
                        ((MainActivity) getActivity()).showSnack("加载失败");
                        binding.swipRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(BaseGankResponse<List<Girl>> response) {
                        for (Girl girl : response.datas) {
                            if (girl.getHeight() == 0) {
                                girl.setHeight((new Random().nextInt(100)) + 500);
                            }
                        }

                        if (isRefresh) {
                            adapter.setNewData(response.datas);
                        } else {
                            adapter.addDatas(response.datas);
                        }
                    }

                });
    }


    /**
     * Recycler的分割线
     */
    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }


}