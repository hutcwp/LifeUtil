package club.hutcwp.lifeutil.http;

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class ApiFactory {

    //用于同步处理
    protected static final Object monitor = new Object();

    private static GirlsController girlsController;

    public static GirlsController getGirlsController(){

        if(girlsController==null){
            synchronized (monitor){
                girlsController =RetrofitManager.getInstance().create(GirlsController.class);
            }
        }

        return girlsController;
    }
}
