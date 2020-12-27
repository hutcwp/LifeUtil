package me.hutcwp.cartoon.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class RetrofitManager {

    private static RetrofitManager instance;
    private static Retrofit retrofit;
    private static Gson gson;
    private static String cookie = "";
    private static final String BASE_URL ="http://liyuyu.cn/";


    public RetrofitManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static void reset() {
        instance = null;
    }

    public <T> T create(Class<T> service) {

        return retrofit.create(service);

    }

    public static RetrofitManager getInstance() {

        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    private static OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    private  Gson gson() {
        if (gson == null) {
            synchronized (RetrofitManager.class) {
                gson = new GsonBuilder().setLenient().create();
            }
        }
        return gson;
    }
}
