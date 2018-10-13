package club.hutcwp.lifeutil.ui.reading;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.model.ReadCategory;
import club.hutcwp.lifeutil.ui.base.IBaseView;
import hut.cwp.mvp.MvpPresenter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hutcwp on 2018/10/13 17:05
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class ReadPresenter extends MvpPresenter<IBaseView> {

    private Subscription subscription;

    /**
     * 获取分类
     */
    public void getCategory() {
        final String host = "http://gank.io/xiandu";

        subscription = Observable.just(host).subscribeOn(Schedulers.io()).map(
                new Func1<String, List<ReadCategory>>() {
                    @Override
                    public List<ReadCategory> call(String s) {
                        List<ReadCategory> list = new ArrayList<>();
                        try {
                            Document doc = Jsoup.connect(host).timeout(5000).get();
                            Element cate = doc.select("div#xiandu_cat").first();
                            Elements links = cate.select("a[href]");
                            for (Element element : links) {
                                ReadCategory category = new ReadCategory();
                                category.setName(element.text());
                                category.setUrl(element.attr("abs:href"));
                                list.add(category);
                                Log.d("test", "name: " + category.getName());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (list.size() > 0) {
                            list.get(0).setUrl(host + "/wow");
                        }
                        return list;
                    }
                }
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ReadCategory>>() {
            @Override
            public void onCompleted() {
                Log.d("test", "complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test", "error");
            }

            @Override
            public void onNext(List<ReadCategory> readCategories) {
//                for (ReadCategory cate : readCategories) {
//                    Log.d("test", "cate: " + cate.getName());
//                }
                IBaseView fragment = getView();
                fragment.initTabLayout(readCategories);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
