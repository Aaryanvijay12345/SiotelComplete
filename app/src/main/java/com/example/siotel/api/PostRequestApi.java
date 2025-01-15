package com.example.siotel.api;

import com.example.siotel.models.HRHDetailsModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.LoginModel;
import com.example.siotel.models.LoginResponseModel;
import com.example.siotel.models.PayModel;
import com.example.siotel.models.RozarPayResponse;
import com.example.siotel.models.RzPayStatus;
import com.example.siotel.models.RzResponse;
import com.example.siotel.models.RzStatusResponse;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.models.TokenCl;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PostRequestApi {
  @POST("auth/")
  Call<TokenCl> LogintoServer(@Body LoginModel loginModel);
  @POST("houseListApi/")  // Ensure this is the correct path
  Call<List<HouseholdsModel>> getAllMeter(
          @Header("Authorization") String token,
          @Body SaveEmail saveEmail);
    @GET()
    Call<List<HouseholdsDetailsModel>> getMetersDtl(@Url String s, @Header("Authorization") String token);
    @GET("logoutApi/")
    Call<Token> logoutApp(@Header("Authorization")String token);
  @GET()
  Call<List<HRHDetailsModel>> getHouseholdsRechargeHistory(@Url String s, @Header("Authorization") String token);
  @POST("RechargesumApi/")
  Call<Integer>getTotalRecharge(@Header("Authorization") String token, @Body SaveEmail saveEmail);

  @POST("payments/MeterRechargeApi")
  Call<RzResponse> getRzPay(@Body PayModel payModel);


  //http://meters.siotel.in:8000/payments/payment_statusApi
  @POST("payments/payment_statusApi")
  Call<RzStatusResponse> getRzPyStatus(@Body RzPayStatus rzPayStatus);
  /*@GET()
  Call<List<HouseholdsDetailsModel>> getMetersDtl(@Url String s, @Header("Authorization") String token); */

}
