package me.black.demolib.http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.black.demolib.http.interceptor.LogInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum RetrofitManager {
    INSTANCE;
    private final OkHttpClient client;
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    RetrofitManager() {
        OkHttpClient.Builder okHttpBuild = new OkHttpClient.Builder();
        client = okHttpBuild.readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .build();
    }

    private Retrofit getRetrofit(String url) {
        if(!retrofitMap.containsKey(url)) {
            Retrofit retrofit =  new Retrofit.Builder()
                    .client(client)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            retrofitMap.put(url, retrofit);
        }
        return retrofitMap.get(url);
    }

    public <T> T create(String url, Class<T> service) {
        return getRetrofit(url).create(service);
    }
}
