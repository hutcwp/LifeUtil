package club.hutcwp.lifeutil.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.hutcwp.lifeutil.entitys.NormalPhoto;

/**
 * Created by hutcwp on 2018/10/14 16:46
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PhotoCatagoryParseImpl extends IParse<NormalPhoto> {

    @Override
    public List<NormalPhoto> parseHtmlFromUrl(String path) {
        List<NormalPhoto> photoList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(path).timeout(5000).get();
            Element element = doc.select(".flex_grid").select(".credits").first();
            Elements items = element.select(".item");
            for (Element ele : items) {
                NormalPhoto item = new NormalPhoto();
                String imgUrl = ele.select("a[href]").first().select("img").first().attr("src");
                String name = ele.select("a[href]").last().text();
                item.setName(name);
                item.setImg(imgUrl);
                item.setDate(getDate(imgUrl));
                photoList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoList;
    }

    /**
     * 根据imgUrl参数获取date
     * imgUrl格式 -> https://cdn.pixabay.com/photo/2018/09/13/02/17/pills-3673645__340.jpg
     * @param imgUrl
     * @return
     */
    private String getDate(String imgUrl) {
        String str1 = imgUrl.replace("https://cdn.pixabay.com/photo/", "");
        String date = str1.substring(0, 10);
        return date;
    }

}
