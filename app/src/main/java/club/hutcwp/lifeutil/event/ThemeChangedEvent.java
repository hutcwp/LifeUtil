package club.hutcwp.lifeutil.event;

/**
 * Created by liyu on 2016/11/29.
 */

public class ThemeChangedEvent {

    public final  int message;

    public ThemeChangedEvent(int message) {
        this.message = message;
    }

    public int getMsg() {
        return message;
    }



}
