package com.example.mimolearningapp.Data.Online;

import com.example.mimolearningapp.Data.Online.Config.ApiConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ClassApiService getClassApiService() {
        return getRetrofitInstance().create(ClassApiService.class);
    }

    public static UserApiService getUserApiService() {
        return getRetrofitInstance().create(UserApiService.class);
    }

}