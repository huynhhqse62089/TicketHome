package com.example.huynhhq.tickethome.apiservice;
import com.example.huynhhq.tickethome.model.Status;
import com.example.huynhhq.tickethome.model.StatusRegister;
import com.example.huynhhq.tickethome.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HuynhHQ on 10/15/2017.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("/user/api_login")
    Call<User> checkLogin(@Field("username") String username, @Field("password") String password);

//    @POST("/users/api_add")
//    Call<Status> createUser(@Body User user);
    @FormUrlEncoded
    @POST("/user/api_add")
    Call<StatusRegister> createUser(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("fullname") String fullname,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("dob") String dob,
                                    @Field("role") int role,
                                    @Field("verifymethod") int verifymethod
    );

    @FormUrlEncoded
    @POST("/user/api_verify")
    Call<StatusRegister> checkVerify(@Field("username") String username,
                                     @Field("verifycode") String verifyCode);

}
