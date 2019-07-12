package com.st18apps.weatherapp.network;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String APP_ID = "f950916da226e94a44f71fd2413140c2";

    private static Retrofit retrofit = buildClient();
    private static APIInterface apiInterface;

    private static Retrofit buildClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new MyInterceptor()).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static APIInterface getApiInterface() {
        if (apiInterface == null) {
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    static class MyInterceptor implements Interceptor {

        @SuppressLint("DefaultLocale")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();

            Request requestWithUserAgent = builder.build();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %n%s", request.url(), requestWithUserAgent.headers()));

            Response response = chain.proceed(requestWithUserAgent);

            long t2 = System.nanoTime();

            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }
}

