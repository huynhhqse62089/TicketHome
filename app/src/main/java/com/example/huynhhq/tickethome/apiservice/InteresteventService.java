package com.example.huynhhq.tickethome.apiservice;

import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Status;
import com.example.huynhhq.tickethome.model.StatusRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HuynhHQ on 11/1/2017.
 */

public interface InteresteventService {

    @FormUrlEncoded
    @POST("/interestevent/api_edit")
    Call<StatusRegister> addInterestevent(@Field("username") String username,
                                          @Field("eventId") int eventId,
                                          @Field("type") int type);

    @FormUrlEncoded
    @POST("/interestevent/api_getByUserType")
    Call<List<Event>> getInterestevent(@Field("username") String username);
}
