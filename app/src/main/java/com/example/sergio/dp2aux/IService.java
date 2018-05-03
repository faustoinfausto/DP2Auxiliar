package com.example.sergio.dp2aux;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IService {

    @POST("sessions")
    Call<JsonObject> select(@Body String params);
}
