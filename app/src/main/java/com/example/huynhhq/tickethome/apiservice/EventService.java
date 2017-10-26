package com.example.huynhhq.tickethome.apiservice;

import com.example.huynhhq.tickethome.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public interface EventService {
    @GET("/event/api_getAll")
    Call<List<Event>> getAllEvent();

    @FormUrlEncoded
    @POST("/event/api_getByCity")
    Call<List<Event>> getByCity(@Field("cityId") int cityId);

//    @POST("events/api_getByCity")
//    Call<List<Event>> getByCity(@Body int cityId);
}
