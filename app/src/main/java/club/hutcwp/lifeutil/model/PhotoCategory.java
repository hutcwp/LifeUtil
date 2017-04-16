package club.hutcwp.lifeutil.model;

/**
 * Created by hutcwp on 2017/4/16.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class PhotoCategory {

    private String name;

    private String url;

    public PhotoCategory(String name, String url) {
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


}
