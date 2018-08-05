package com.example.sunyton.diaryio.http;

import com.example.sunyton.diaryio.Const;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient;


    private ApiClient() {

    }

    public static Retrofit getClient() {

        if (mOkHttpClient == null) {
            initHttpClient();
        }

        if (mRetrofit == null) {
            synchronized (ApiClient.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(Const.BASE_URL)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                }
            }

        }

        return mRetrofit;
    }

    private static void initHttpClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .addHeader("x-bmob-application-id", "48f70f7f2138e0be0865014241855954")
                                .addHeader("x-bmob-rest-api-key", "bd0e2dfeb5705c7efc253abaa1367de5");

                        Request request = builder.build();
                        return chain.proceed(request);
                    }

                })
                .build();


    }


}
