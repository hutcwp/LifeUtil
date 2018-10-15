package club.hutcwp.lifeutil.entitys;

/**
 * Created by hutcwp on 2017/4/16.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class PhotoCategory {

    private String name;
    private int type = 0; // 0表示走api ，1表示抓包
    private String url;

    public PhotoCategory(int type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
