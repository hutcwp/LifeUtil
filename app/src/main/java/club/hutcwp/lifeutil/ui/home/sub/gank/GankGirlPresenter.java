package club.hutcwp.lifeutil.ui.home.sub.gank;

import android.util.Log;

import java.util.List;
import java.util.Random;

import club.hutcwp.lifeutil.http.ApiFactory;
import club.hutcwp.lifeutil.http.BaseGankResponse;
import club.hutcwp.lifeutil.model.Girl;
import hut.cwp.mvp.MvpPresenter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hutcwp on 2018/10/13 20:45
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class GankGirlPresenter extends MvpPresenter<IGankGril> {

    private boolean isRefresh = true;

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
                        getView().showSnack("加载完成");

                        getView().setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("teste", e.getMessage());
                        getView().showSnack("加载失败");
                        getView().setRefreshing(false);
                    }

                    @Override
                    public void onNext(BaseGankResponse<List<Girl>> response) {
                        for (Girl girl : response.datas) {
                            if (girl.getHeight() == 0) {
                                girl.setHeight((new Random().nextInt(100)) + 500);
                            }
                        }

                        if (isRefresh()) {
                            getView().setNewData(response.datas);
                        } else {
                            getView().addNewData(response.datas);
                        }
                    }

                });
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void serRefresh(boolean state) {
        this.isRefresh = state;
    }
}
