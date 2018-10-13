package club.hutcwp.lifeutil.ui.home.sub.gank;

import java.util.List;

import club.hutcwp.lifeutil.model.Girl;
import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2018/10/13 21:08
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public interface IGankGril extends MvpView{

    void showSnack(String msg);

    void setRefreshing(boolean status);

    void setNewData(List<Girl> data);

    void addNewData(List<Girl> data);

}
