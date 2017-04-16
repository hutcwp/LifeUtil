package club.hutcwp.lifeutil.util;

/**
 * Created by hutcwp on 2017/4/15.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class DoubleClickExit {

    public static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }
}
