package com.example.mimolearningapp.Data.Online;

import com.example.mimolearningapp.Model.LoginRequest;
import com.example.mimolearningapp.Model.LoginResponse;
import com.example.mimolearningapp.Model.UpdateProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("api/User/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/User/update")
    Call<LoginResponse> updateProfile(@Body UpdateProfileRequest request);

}