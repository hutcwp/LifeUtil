package club.hutcwp.lifeutil.ui.home.sub.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.model.ReadItem;
import hut.cwp.mvp.MvpPresenter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hutcwp on 2018/10/13 22:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class NewsPresenter extends MvpPresenter<ICategory> {

    private Subscription subscription;
    private int cutPage = 0;

    /**
     * 从服务器上获取数据
     */
    public void getDataFromServer() {
        String baseUrl = getArguments().getString("url");
        final String url = baseUrl + "/page/" + cutPage;

        getView().setRefreshing(true);
        subscription = Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, List<ReadItem>>() {
            @Override
            public List<ReadItem> call(String s) {
                List<ReadItem> readList = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(5000).get();
                    Element element = doc.select("div.xiandu_items").first();
                    Elements items = element.select("div.xiandu_item");
                    for (Element ele : items) {
                        ReadItem item = new ReadItem();
                        Element left = ele.select("div.xiandu_left").first();
                        Element right = ele.select("div.xiandu_right").first();

                        item.setName(left.select("a[href]").text());
                        item.setFrom(right.select("a").attr("title"));
                        item.setUpdateTime(left.select("span").select("small").text());
                        item.setUrl(left.select("a[href]").attr("href"));
                        item.setIcon(right.select("img").first().attr("src"));
                        readList.add(item);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return readList;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ReadItem>>() {
            @Override
            public void onCompleted() {
                getView().setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                getView().setRefreshing(false);
            }

            @Override
            public void onNext(List<ReadItem> list) {
                cutPage++;

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
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
