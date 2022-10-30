package club.hutcwp.lifeutil.ui.home.top

import java.util.ArrayList

import club.hutcwp.lifeutil.entitys.PhotoCategory
import com.example.presenter.core.MvpPresenter

/**
 * Created by hutcwp on 2018/10/14 00:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class PhotoPresenter : MvpPresenter<PhotoFragment>() {

    private val photoCategories = ArrayList<PhotoCategory>()

    fun getCategory() {
        initCategorys()
        if (view != null) {
            view!!.initTabLayout(photoCategories)
        }
    }

    fun initCategorys() {
        photoCategories.add(PhotoCategory(0, "靓女专题", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/"))
        photoCategories.add(PhotoCategory(1, "插画", "https://pixabay.com/zh/editors_choice/?media_type=illustration&pagi="))
        photoCategories.add(PhotoCategory(1, "矢量图", "https://pixabay.com/zh/editors_choice/?media_type=vector&pagi="))
        photoCategories.add(PhotoCategory(1, "照片", "https://pixabay.com/zh/editors_choice/?media_type=photo&pagi="))
    }
}
