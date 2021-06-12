package com.hutcwp.read.core

import org.jsoup.Jsoup

import java.io.IOException
import java.util.ArrayList

import com.hutcwp.read.entitys.NormalPhoto

/**
 * Created by hutcwp on 2018/10/14 16:46
 *
 *
 */
class PhotoCatagoryParseImpl : IParse<NormalPhoto>() {

    override fun parseHtmlFromUrl(path: String): List<NormalPhoto> {
        val photoList = ArrayList<NormalPhoto>()
        try {
            val doc = Jsoup.connect(path).timeout(5000).get()
            val element = doc.select(".flex_grid").select(".credits").first()
            val items = element.select(".item")
            for (ele in items) {
                val item = NormalPhoto()
                val imgUrl = ele.select("a[href]").first().select("img").first().attr("src")
                val name = ele.select("a[href]").last().text()
                item.name = name
                item.img = imgUrl
                item.date = getDate(imgUrl)
                photoList.add(item)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return photoList
    }

    /**
     * 根据imgUrl参数获取date
     * imgUrl格式 -> https://cdn.pixabay.com/photo/2018/09/13/02/17/pills-3673645__340.jpg
     * @param imgUrl
     * @return
     */
    private fun getDate(imgUrl: String): String {
        val str1 = imgUrl.replace("https://cdn.pixabay.com/photo/", "")
        return str1.substring(0, 10)
    }

}
