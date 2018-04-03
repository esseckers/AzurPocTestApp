package com.testapp.azurpoc.azurpoctestapp.remote;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public class ApiConnection {

    public static ServiceApi provideServiceApi() {
        return provideRetrofitAdapter().create(ServiceApi.class);
    }

    private static Retrofit provideRetrofitAdapter() {
        return new Retrofit.Builder()
                .baseUrl(Environment.SERVER_REST)
                .addConverterFactory(JacksonConverterFactory.create(provideObjectMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideHttpClient())
                .build();
    }

    private static OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .followRedirects(false);
        if (Environment.DEBUG) builder.addInterceptor(logging);
        return builder.build();
    }

    private static Interceptor getInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            okhttp3.Response response = chain.proceed(builder.build());
            if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new IOException();
            }
            return response;
        };
    }

    private static ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}