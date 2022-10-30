package me.hutcwp.auto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author billy.qi
 * @since 17/9/20 16:56
 */
public class MainPageManager {

    private static Set<IMainPage> CATEGORIES = new HashSet<>();

    public static void register(IMainPage category) {
        if (category != null) {
            CATEGORIES.add(category);
        }
    }

    public static Set<IMainPage> getCategoryNames() {
        return CATEGORIES;
    }
}
