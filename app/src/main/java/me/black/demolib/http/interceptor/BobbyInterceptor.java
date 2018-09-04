package me.black.demolib.http.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.black.demolib.http.HttpParamsUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 统一添加一些固定请求参数
 * 注意：目前只实现GET方式
 * Create by maodq on 2018/5/11
 */
public class BobbyInterceptor implements Interceptor {
    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl originalUrl = request.url();
        HashMap<String, String> params = new HashMap<>();
        for (int i = 0; i < originalUrl.querySize(); i++) {
            String name = originalUrl.queryParameterName(i);
            String value = originalUrl.queryParameterValue(i);
            params.put(name, value);
        }

        // 添加签名
        HttpParamsUtil.append_timestamp_nonce_appId_lang(params);
        HttpParamsUtil.finallyAppendSignature(params);

        HttpUrl.Builder newUrlBuilder = originalUrl.newBuilder();

        clearAllParams(originalUrl, newUrlBuilder);
        addNewParams(params, newUrlBuilder);

        HttpUrl newUrl = newUrlBuilder.build();
        Request newRequest = request.newBuilder().url(newUrl).build();
        return chain.proceed(newRequest);
    }

    // 增加新的请求参数到url中
    private void addNewParams(Map<String, String> params, HttpUrl.Builder builder) {
        for (String key : params.keySet()) {
            String value = params.get(key);
            builder.addQueryParameter(key, value);
        }
    }

    // 清除url中的所有请求参数
    private void clearAllParams(HttpUrl originalHttpUrl, HttpUrl.Builder builder) {
        Set<String> queryParameterNames = originalHttpUrl.queryParameterNames();
        for (String name : queryParameterNames) {
            builder.removeAllQueryParameters(name);
        }
    }
}
