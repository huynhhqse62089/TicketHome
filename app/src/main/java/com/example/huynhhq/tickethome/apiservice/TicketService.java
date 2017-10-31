package com.example.huynhhq.tickethome.apiservice;

import com.example.huynhhq.tickethome.model.StatusRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HuynhHQ on 10/29/2017.
 */

public interface TicketService {
    @FormUrlEncoded
    @POST("/ticket/api_addNewTicket")
    Call<StatusRegister> bookTicket(@Field("eventId") int eventId,
                                    @Field("username") String username,
                                    @Field("description") String description,
                                    @Field("position") String position,
                                    @Field("available") boolean available,
                                    @Field("price") String price,
                                    @Field("paymentMethod") String paymentMethod,
                                    @Field("shipAddress") String shipAddress,
                                    @Field("isPaid") boolean isPaid,
                                    @Field("quantity") int quantity
    );
}
