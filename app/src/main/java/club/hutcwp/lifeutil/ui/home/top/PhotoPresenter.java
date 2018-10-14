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
        photoCategories.add(new PhotoCategory("插画", "https://pixabay.com/zh/editors_choice/?media_type=illustration&pagi=1"));
        photoCategories.add(new PhotoCategory("矢量图", "https://pixabay.com/zh/editors_choice/?media_type=vector"));
        photoCategories.add(new PhotoCategory("照片", "https://pixabay.com/zh/editors_choice/?media_type=photo"));
//        photoCategories.add(new PhotoCategory("影视", "https://pixabay.com/zh/editors_choice/?media_type=video"));
    }
}
