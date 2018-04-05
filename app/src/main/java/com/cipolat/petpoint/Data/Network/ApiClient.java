package com.cipolat.petpoint.Data.Network;

/**
 * Created by sebastian on 22/07/17.
 */

import com.cipolat.petpoint.Data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofitJSON = null;

    public static OkHttpClient getOKClient() {
        OkHttpClient client;
        OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
        clientbuilder.followRedirects(true);
        client = clientbuilder.build();
        return client;
    }

    public static Retrofit getClient() {
        if (retrofitJSON == null) {
            retrofitJSON = new Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .client(getOKClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitJSON;
    }

}