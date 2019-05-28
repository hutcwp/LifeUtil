package club.hutcwp.lifeutil.ui.home.top;

import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.entitys.PhotoCategory;
import hut.cwp.mvp.MvpPresenter;

/**
 * Created by hutcwp on 2018/10/14 00:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PhotoPresenter extends MvpPresenter<PhotoFragment> {

    private List<PhotoCategory> photoCategories = new ArrayList<>();

    public void getCategory() {
        initCategorys();
        if (getView() != null) {
            getView().initTabLayout(photoCategories);
        }
    }

    public void initCategorys() {
        photoCategories.add(new PhotoCategory(0, "靓女专题", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/"));
        photoCategories.add(new PhotoCategory(1, "插画", "https://pixabay.com/zh/editors_choice/?media_type=illustration&pagi="));
        photoCategories.add(new PhotoCategory(1, "矢量图", "https://pixabay.com/zh/editors_choice/?media_type=vector&pagi="));
        photoCategories.add(new PhotoCategory(1, "照片", "https://pixabay.com/zh/editors_choice/?media_type=photo&pagi="));
    }
}
