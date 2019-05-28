package club.hutcwp.lifeutil.core

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.ArrayList

import club.hutcwp.lifeutil.entitys.News

/**
 * Created by hutcwp on 2018/10/14 17:04
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class NewsParseImpl : IParse<News>() {

    override fun parseHtmlFromUrl(path: String): List<News> {
        val readList = ArrayList<News>()
        try {
            val doc = Jsoup.connect(path).timeout(5000).get()
            val element = doc.select("div.xiandu_items").first()
            val items = element.select("div.xiandu_item")
            for (ele in items) {
                val item = News()
                val left = ele.select("div.xiandu_left").first()
                val right = ele.select("div.xiandu_right").first()

                item.name = left.select("a[href]").text()
                item.from = right.select("a").attr("title")
                item.updateTime = left.select("span").select("small").text()
                item.url = left.select("a[href]").attr("href")
                item.icon = right.select("img").first().attr("src")
                readList.add(item)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return readList
    }
}
