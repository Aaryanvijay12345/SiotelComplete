package com.example.siotel.api;

import com.example.siotel.models.ConsumerMeterInformationModel;
import com.example.siotel.models.EmailRequest;
import com.example.siotel.models.HRHDetailsModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.Invoice;
import com.example.siotel.models.InvoiceCreationRequest;
import com.example.siotel.models.InvoiceCreationResponse;
import com.example.siotel.models.InvoiceDetail;
import com.example.siotel.models.InvoiceRequest;
import com.example.siotel.models.InvoiceResponse;
import com.example.siotel.models.LoginModel;
import com.example.siotel.models.PayModel;
import com.example.siotel.models.RechargeHistoryItem;
import com.example.siotel.models.ReportRequest;
import com.example.siotel.models.ReportResponse;
import com.example.siotel.models.RozarPayResponse;
import com.example.siotel.models.RzPayStatus;
import com.example.siotel.models.RzResponse;
import com.example.siotel.models.RzStatusResponse;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.models.TokenCl;
//import com.example.siotel.models.MeterDataResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PostRequestApi {
  @POST("auth/")
  Call<TokenCl> LogintoServer(@Body LoginModel loginModel);

  @POST("houseListApi/")
  Call<List<HouseholdsModel>> getAllMeter(
          @Header("Authorization") String token,
          @Body SaveEmail saveEmail);

  @GET()
  Call<List<HouseholdsDetailsModel>> getMetersDtl(@Url String s, @Header("Authorization") String token);

  @GET("logoutApi/")
  Call<Token> logoutApp(@Header("Authorization") String token);

  @GET()
  Call<List<HRHDetailsModel>> getHouseholdsRechargeHistory(@Url String s, @Header("Authorization") String token);

  @POST("RechargesumApi/")
  Call<Integer> getTotalRecharge(@Header("Authorization") String token, @Body SaveEmail saveEmail);

  @POST("payments/MeterRechargeApi")
  Call<RzResponse> getRzPay(@Body PayModel payModel);

  @POST("payments/MeterRechargeApi")
  Call<RzResponse> getMeterRechargeApi(@Body PayModel payModel);

  @POST("payments/payment_statusApi")
  Call<RzStatusResponse> getRzPyStatus(@Body RzPayStatus rzPayStatus);

  @POST("payments/payment_statusApi")
  Call<RozarPayResponse> postPaymentStatus(@Body Map<String, String> response);

  @POST("MeterdataApi2/{meterSno}")
  Call<List<ConsumerMeterInformationModel>> getMetersDtl1(
          @Path("meterSno") String meterSno,
          @Header("Authorization") String token,
          @Body RequestBody requestBody
  );

  @POST("RechargeHistoryApi/{meterSno}")
  Call<List<RechargeHistoryItem>> getRechargeHistory(
          @Path("meterSno") String meterSno,
          @Header("Authorization") String token,
          @Body RequestBody requestBody
  );



  @POST("UsageReportsApi/{meterSNO}")
  Call<ReportResponse> getUsageReport(
          @Path("meterSNO") String meterSNO,
          @Header("Authorization") String token,
          @Body ReportRequest request
  );

  @POST("houseListApi/")
  Call<List<HouseholdsModel>> getAllSites(
          @Header("Authorization") String token,
          @Body SaveEmail saveEmail);

  @POST("invoice/invoice-list-API/")
  Call<List<Invoice>> getInvoiceList(
          @Header("Authorization") String token,
          @Body EmailRequest emailRequest
  );

  @GET("invoice/invoice-detail-API/{id}")
  Call<InvoiceDetail> getInvoiceDetail(@Path("id") int invoiceId);

  // New method for invoice
  // New method for invoice creation
  @POST("invoice/InvoiceMidAPI/")
  Call<InvoiceResponse> createInvoice(
          @Header("Authorization") String token,
          @Body InvoiceRequest request
  );

  @POST("invoice/create-invoice-API/")
  Call<String> createInvoiceApi(
          @Header("Authorization") String token,
          @Body InvoiceCreationRequest request
  );







//  @GET("MeterdataApi/{meterSno}")
//  Call<MeterDataResponse> getMeterData(@Path("meterSno") String meterSno);
}
