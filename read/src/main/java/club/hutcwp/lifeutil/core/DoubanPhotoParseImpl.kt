package club.hutcwp.lifeutil.core

import club.hutcwp.lifeutil.entitys.NormalPhoto
import club.hutcwp.lifeutil.entitys.PhotoBean
import com.google.gson.Gson
import me.hutcwp.log.MLog
import java.util.*


/**
 *
 * Created by hutcwp on 2020-03-04 17:01
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class DoubanPhotoParseImpl : IParse<NormalPhoto>() {


    override fun parse(content: String): List<NormalPhoto> {
        val photoList = ArrayList<NormalPhoto>()
        MLog.info(TAG, "parseHtmlFromUrl onResponse, response=$content")
        val photoJsonBean = Gson().fromJson(content, PhotoBean::class.java)
        photoJsonBean?.results?.forEach {
            val normalPhoto = NormalPhoto()
            normalPhoto.img = it.image_url
            normalPhoto.name = it.title
            normalPhoto.date = "今天"
            photoList.add(normalPhoto)
        }
        return photoList
    }


    companion object {
        private const val TAG = "DoubanPhotoParseImpl"
    }
}