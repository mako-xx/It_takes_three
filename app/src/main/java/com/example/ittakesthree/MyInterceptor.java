package com.example.ittakesthree;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        final String url = request.url().url().toString();
        Log.e("INFO", "url: " + url);
        Log.e("INFO", "该请求被拦截");
        return chain.proceed(request);
    }
}
