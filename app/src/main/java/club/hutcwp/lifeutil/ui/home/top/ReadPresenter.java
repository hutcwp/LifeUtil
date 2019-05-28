package club.hutcwp.lifeutil.ui.home.top;

import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.app.App;
import club.hutcwp.lifeutil.entitys.ReadCategory;
import hut.cwp.mvp.MvpPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hutcwp on 2018/10/13 17:05
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class ReadPresenter extends MvpPresenter<ReadFragment> {

    private Disposable disposable;

    /**
     * 获取分类
     */
    public void getCategory() {
        final String host = "http://gank.io/xiandu";

        disposable = Observable.just(host).subscribeOn(Schedulers.io()).map(
                new Function<String, List<ReadCategory>>() {
                    @Override
                    public List<ReadCategory> apply(String s) throws Exception {
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
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<ReadCategory>>() {
            @Override
            public void accept(List<ReadCategory> readCategories) {
                if (getView() != null) {
                    getView().initTabLayout(readCategories);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Toast.makeText(App.getContext(), "解析发生过程!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

}
