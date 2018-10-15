package club.hutcwp.lifeutil.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.entitys.News;

/**
 * Created by hutcwp on 2018/10/14 17:04
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class NewsParseImpl extends IParse<News> {

    @Override
    public List<News> parseHtmlFromUrl(String path) {
        List<News> readList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(path).timeout(5000).get();
            Element element = doc.select("div.xiandu_items").first();
            Elements items = element.select("div.xiandu_item");
            for (Element ele : items) {
                News item = new News();
                Element left = ele.select("div.xiandu_left").first();
                Element right = ele.select("div.xiandu_right").first();

                item.setName(left.select("a[href]").text());
                item.setFrom(right.select("a").attr("title"));
                item.setUpdateTime(left.select("span").select("small").text());
                item.setUrl(left.select("a[href]").attr("href"));
                item.setIcon(right.select("img").first().attr("src"));
                readList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readList;
    }
}
