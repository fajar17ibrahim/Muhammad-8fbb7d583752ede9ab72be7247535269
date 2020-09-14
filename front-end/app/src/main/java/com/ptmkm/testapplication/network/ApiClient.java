package com.ptmkm.testapplication.network;

import com.ptmkm.testapplication.utils.ApiUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

}
