package com.example.siotel.api;

import com.example.siotel.activity.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PoatRegisterApi {
    @FormUrlEncoded
    @POST("account/api/register")
    Call<RegisterModel> RegistertoServer(@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("first_name") String first_name,
                                         @Field("last_name") String last_name,
                                         @Field("email") String email);
}
