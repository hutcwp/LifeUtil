package club.hutcwp.lifeutil.ui.home.top;

import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.model.PhotoCategory;
import hut.cwp.mvp.MvpPresenter;

/**
 * Created by hutcwp on 2018/10/14 00:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PhotoPresenter extends MvpPresenter<IHome>{

    private List<PhotoCategory> photoCategories = new ArrayList<>();


    public void getCategory(){
        initCategorys();
        getView().initTabLayout(photoCategories);
    }

    public void initCategorys() {
        photoCategories.add(new PhotoCategory("摄影世界", "http://www.egouz.com/pics/icon/"));
        photoCategories.add(new PhotoCategory("插画设计", "http://www.egouz.com/pics/vector/"));
        photoCategories.add(new PhotoCategory("桌面壁纸", "http://www.egouz.com/pics/wallpaper/"));
        photoCategories.add(new PhotoCategory("艺术人生", "http://www.egouz.com/pics/pattern/"));
    }
}
