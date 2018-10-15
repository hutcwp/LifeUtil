package club.hutcwp.lifeutil.ui.home.sub;

import java.util.List;

import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2018/10/14 18:37
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public interface IBase<T> extends MvpView {

    void showSnack(String msg);

    void setRefreshing(boolean status);

    void setNewData(List<T> data);

    void addNewData(List<T> data);
}
