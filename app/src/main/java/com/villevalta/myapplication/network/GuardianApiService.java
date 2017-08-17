package com.villevalta.myapplication.network;

import com.villevalta.myapplication.model.Page;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by villevalta on 8/14/17.
 */

public class GuardianApiService {

    private GuardianApiServiceInterface serviceInterface;
    private OkHttpClient client;

    public GuardianApiService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        serviceInterface = new Retrofit.Builder()
                .baseUrl("https://content.guardianapis.com/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(GuardianApiServiceInterface.class);

    }

    public Call<Page> search(String searchQuery, int page){
        return serviceInterface.search(searchQuery, "test", page);
    }

    public OkHttpClient getClient() {
        return client;
    }
}
