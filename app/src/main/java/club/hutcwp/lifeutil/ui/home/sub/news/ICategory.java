package club.hutcwp.lifeutil.ui.home.sub.news;

import java.util.List;

import club.hutcwp.lifeutil.model.ReadItem;
import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2018/10/13 22:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public interface ICategory extends MvpView{

    void showSnack(String msg);

    void setRefreshing(boolean status);

    void setNewData(List<ReadItem> data);

    void addNewData(int pos,List<ReadItem> data);

    List<ReadItem> getData();
}
