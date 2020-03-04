package me.hutcwp.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.hutcwp.log.MLog;

/**
 * Created by hutcwp on 2020-03-04 15:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class RxUtils {
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static Consumer<? super Throwable> errorConsumer(final String tag) {
        return errorConsumer(tag, null);
    }

    public static Consumer<? super Throwable> errorConsumer(
            final String tag, final String msg) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                MLog.error(tag, msg, throwable);
            }
        };
    }
}
