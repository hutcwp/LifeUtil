package hut.cwp.api;

import android.app.Activity;

import hut.cwp.util.MLog;

import static android.content.ContentValues.TAG;

/**
 * Created by hutcwp on 2018/4/19.
 */


public class Injector {

    private static final String CONTAINER_SUFFIX = "$$ComponentInject";

    public static void injectContainer(Activity activity) {
        Inject proxyActivity = findProxyActivity(activity, CONTAINER_SUFFIX);
        proxyActivity.inject(activity, activity);
    }

    private static Inject findProxyActivity(Object activity, String suffix) {
        try {
            Class clazz = activity.getClass();
            String name = clazz.getName() + suffix;
            Class injectorClazz = Class.forName(name);
            return (Inject) injectorClazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    String.format("ClassNotFoundException :" +
                            " %s is not found ,make sure you have defined component with annotation @InitAttrConfigs[]", activity.getClass().getSimpleName() + suffix));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("There have a exception from Inject [MvpPresenter]");
    }
}
