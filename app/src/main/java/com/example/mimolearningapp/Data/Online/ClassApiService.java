package com.example.mimolearningapp.Data.Online;

import com.example.mimolearningapp.Model.ClassItem;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface ClassApiService {
    @GET("api/Class")
    Call<List<ClassItem>> getAllClasses();
}