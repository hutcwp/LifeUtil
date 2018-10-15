package club.hutcwp.lifeutil.ui.home.sub.news;

import java.util.List;

import club.hutcwp.lifeutil.entitys.News;
import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2018/10/13 22:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public interface INews extends MvpView {


    void setRefreshing(boolean status);

    void setNewData(List<News> data);

    void addNewData(int pos, List<News> data);

    List<News> getData();
}
