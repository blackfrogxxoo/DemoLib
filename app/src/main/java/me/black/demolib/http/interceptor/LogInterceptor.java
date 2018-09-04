package me.black.demolib.http.interceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import me.black.library.BuildConfig;
import me.black.library.util.LogUtil;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LogInterceptor implements Interceptor {
    @Override
    @SuppressWarnings("ALL")
    public okhttp3.Response intercept(Chain chain) throws IOException {
        if (!BuildConfig.DEBUG) { // 非测试模式不打印日志{
            return chain.proceed(chain.request());
        }
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("params", createHttpHeaderParams())
//                .addHeader("Cookie", "XDEBUG_SESSION=PHPSTORM")
                .build();

        RequestBody body = request.body();
        StringBuilder params = new StringBuilder();
        StringBuilder result = new StringBuilder();
        generateParamsAndResult(body, result, params);
        String tag = request.url().toString();
        List<String> paths = request.url().pathSegments();
        if (paths != null && paths.size() > 0) {
            tag = paths.get(paths.size() - 1);
        }
        LogUtil.i(tag, String.format("Request:\nmethod=%s, url=%s%s\n%s", request.method(),
                request.url(), params, result));
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        LogUtil.i(tag, String.format(Locale.getDefault(),
                "Response:\nReceived response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.json(tag, content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    private String createHttpHeaderParams() {

        return "";
    }

    private void generateParamsAndResult(RequestBody body, StringBuilder result,
                                         StringBuilder params) {
        if (body instanceof MultipartBody) { // 获取MultipartBody中的Parts中的Headers
            result.append("Multipart:\n");
            MultipartBody multipartBody = (MultipartBody) body;
            for (MultipartBody.Part part : multipartBody.parts()) {
                try {
                    Field headers = part.getClass().getDeclaredField("headers");
                    headers.setAccessible(true);
                    final Headers headers2 = (Headers) headers.get(part);
                    result
                            .append("\t")
                            .append(headers2.toString());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else if (body instanceof FormBody) {
            result.append("Form:\n");
            params.append("?");
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                params
                        .append(formBody.name(i))
                        .append("=")
                        .append(formBody.value(i))
                        .append("&");
                result
                        .append("\t")
                        .append(formBody.name(i)).append(": ")
                        .append("\t")
                        .append(formBody.value(i)).append("\n");
            }
            if (params.length() > 0)
                params.deleteCharAt(params.length() - 1);
        }
    }

    public static class HeaderParams {
        
    }
}
