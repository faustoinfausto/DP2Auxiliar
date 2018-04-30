package com.example.sergio.dp2aux;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IService {

    @GET("recognition")
    Call<JsonObject> select();
}
