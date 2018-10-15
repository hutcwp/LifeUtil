package club.hutcwp.lifeutil.ui.home.sub.news;

import java.util.List;

import club.hutcwp.lifeutil.core.NewsParseImpl;
import club.hutcwp.lifeutil.entitys.News;
import hut.cwp.mvp.MvpPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hutcwp on 2018/10/13 22:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class NewsPresenter extends MvpPresenter<INews> {

    private Disposable disposable;
    private int curPage = 0;


    public void getDataFromServer() {
        String baseUrl = getArguments().getString("url");
        final String url = baseUrl + "/page/" + curPage;

        getView().setRefreshing(true);
        disposable = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<News>>() {
                    @Override
                    public List<News> apply(String url) throws Exception {
                        return new NewsParseImpl().parseHtmlFromUrl(url);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<News>>() {
                    @Override
                    public void accept(List<News> list) throws Exception {
                        getView().setRefreshing(false);
                        curPage++;
                        if (getView().getData() == null || getView().getData().size() == 0) {
                            getView().setNewData(list);
                        } else {
                            getView().addNewData(getView().getData().size(), list);
                        }
                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

}
