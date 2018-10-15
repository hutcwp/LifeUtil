package club.hutcwp.lifeutil.ui.home.sub.picture;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.core.PhotoCatagoryParseImpl;
import club.hutcwp.lifeutil.entitys.GankGirlPhoto;
import club.hutcwp.lifeutil.entitys.Photo;
import club.hutcwp.lifeutil.http.ApiFactory;
import club.hutcwp.lifeutil.http.BaseGankResponse;
import hut.cwp.mvp.MvpPresenter;
import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hutcwp on 2018/10/13 20:45
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/

public class PicturePresenter extends MvpPresenter<IPicture> {

    private static final String TAG = "PicturePresenter";

    private int curPage = 1;
    private boolean isRefresh = true;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 获取数据
     */
    public void getGank() {
        getData(getGankObservable());
    }


    private Observable<List<Photo>> getGankObservable() {
        return ApiFactory.getGirlsController().getGank(curPage + "").
                subscribeOn(Schedulers.io())
                .map(new Function<BaseGankResponse<List<GankGirlPhoto>>, List<Photo>>() {
                    @Override
                    public List<Photo> apply(BaseGankResponse<List<GankGirlPhoto>> response) throws Exception {
                        List<Photo> photos = new ArrayList<>();
                        photos.addAll(response.datas);
                        return photos;
                    }
                });
    }


    private void getData(Observable<List<Photo>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        curPage++;
                        if (isRefresh()) {
                            getView().setNewData(photos);
                        } else {
                            getView().addNewData(photos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showSnack("加载失败");
                        getView().setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        getView().showSnack("加载完成");
                        getView().setRefreshing(false);
                    }
                });

    }


    public void getServer() {
        getData(getDataFromServer());
    }

    /**
     * 从服务器上获取数据
     */
    private Observable<List<Photo>> getDataFromServer() {
        final String url = getArguments().getString("url") + curPage;
        return Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<Photo>>() {
                    @Override
                    public List<Photo> apply(String path) throws Exception {
                        List<Photo> photos = new ArrayList<>();
                        photos.addAll(new PhotoCatagoryParseImpl().parseHtmlFromUrl(path));
                        return photos;
                    }
                });
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void serRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
        if (isRefresh) {
            curPage = 1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
