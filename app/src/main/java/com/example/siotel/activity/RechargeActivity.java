//package com.example.siotel.activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.FragmentActivity;
//
//import android.animation.ValueAnimator;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.airbnb.lottie.LottieAnimationView;
//import com.example.siotel.R;
//import com.example.siotel.SharedPrefManager;
//import com.example.siotel.api.PostRequestApi;
//import com.example.siotel.interfaces.HouseHoldId;
//import com.example.siotel.models.PayModel;
//import com.example.siotel.models.RozarPayResponse;
//import com.example.siotel.models.RzPayStatus;
//import com.example.siotel.models.RzResponse;
//import com.example.siotel.models.RzStatusResponse;
//import com.example.siotel.models.Token;
//import com.razorpay.Checkout;
//import com.razorpay.PaymentData;
//import com.razorpay.PaymentResultListener;
//import com.razorpay.PaymentResultWithDataListener;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import java.util.concurrent.TimeUnit;
//
//public class RechargeActivity extends AppCompatActivity  implements PaymentResultWithDataListener {
//
//
//
//
//    SharedPrefManager sharedPrefManager;
//    Token token;
//    String eml,phn;
//
//    RzResponse rzResponse;
//    JSONObject js=new JSONObject();
//
//
//    EditText amount,phone;
//    TextView email,hid,retu;
//
//    Button button;
//
//    String householdId="";
//
//    PaymentData pd;
//    String rozarPayPaymentId="";
//    int    rozarPayPaymentCode=0;
//    LottieAnimationView lottieAnimationView;
//
//    private static final String TAG = RechargeActivity.class.getSimpleName();
//
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recharge);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        toolbar.setTitle("Payment");
//
////        getSupportActionBar().setDisplayShowHomeEnabled(true);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(toolbar);
//        //getSupportActionBar().setDisplayShowHomeEnabled(true);
//        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                onBackPressed();
////            }
////        });
//
//        //  getSupportActionBar().setTitle("Payment");
//
//
//        lottieAnimationView=findViewById(R.id.animation_view);
//
//        lottieAnimationView.addAnimatorUpdateListener((animation) -> {
//            // Do something.
//        });
//        lottieAnimationView.playAnimation();
//
//        if (lottieAnimationView.isAnimating()) {
//            // Do something.
//        }
//
//        // Custom animation speed or duration.
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
//        animator.addUpdateListener(animation -> {lottieAnimationView.setProgress((Float) animation.getAnimatedValue());});
//        animator.start();
//
//
//
//
//        sharedPrefManager=new SharedPrefManager(getApplicationContext());
//        token=sharedPrefManager.getUser();
//
//
//        rzResponse=new RzResponse();
//
//
//
//        Intent intent=getIntent();
//
//        householdId+=intent.getStringExtra("Hid");
//        email=findViewById(R.id.remail);
//        amount=findViewById(R.id.ramount);
//        phone=findViewById(R.id.rphone);
//        hid=findViewById(R.id.rhid);
//        button=findViewById(R.id.rrecharge);
//        retu=findViewById(R.id.retu);
//
//        phn=phone.getText().toString();
//        eml=token.getEmail();
//        email.setText(eml);
//        hid.setText(householdId);
//
//
//
//
//        onClick();
//
//
//    }
//
//    private Retrofit getRetrofitInstance() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)  // Increase connection timeout
//                .readTimeout(60, TimeUnit.SECONDS)     // Increase read timeout
//                .writeTimeout(60, TimeUnit.SECONDS)    // Increase write timeout
//                .retryOnConnectionFailure(true)        // Enable automatic retries
//                .build();
//        return new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient) // Apply the OkHttpClient with timeouts
//                .build();
//    }
//
//
//    public void onClick() {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(amount.getText())) {
//                    amount.setError("Please enter amount");
//                } else if (TextUtils.isEmpty(phone.getText())) {
//                    phone.setError("Please enter phone number");
//                } else if (TextUtils.isEmpty(email.getText())) {
//                    email.setError("Please enter email");
//                } else {
//                    // Preload Razorpay checkout
//                    Checkout.preload(getApplicationContext());
//                    // Call the API to initiate payment
//                    rozroPayApi();
//                }
//            }
//        });
//    }
//
//
//
//    public void startPayment(String orderId, String key) {
//        int intAmt = Math.round(Float.parseFloat(amount.getText().toString())) * 100;
//        Checkout checkout = new Checkout();
//
//        checkout.setKeyID(key);  // Use the key received from the API
//
//        checkout.setImage(R.drawable.gr1);
//
//        final FragmentActivity activity = this;
//
//        try {
//            JSONObject options = new JSONObject();
//
//            options.put("name", token.getEmail());
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", orderId); // Use the order_id received from the API
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "INR");
//            options.put("amount", intAmt); // Pass amount in currency subunits
//            options.put("prefill.email", eml);
//            options.put("prefill.contact", phone.getText().toString());
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);
//
//            checkout.open(activity, options); // Open Razorpay checkout
//
//        } catch (Exception e) {
//            Log.v(TAG, e.toString());
//            Toast.makeText(RechargeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
//        if (paymentData != null) {
//            try {
//                // Create response object with only required data
//                RozarPayResponse response = new RozarPayResponse();
//
//
//                // Fetch devEui and email from shared preferences or database
//                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
//                String devEui = prefs.getString("devEui", householdId); // Replace with actual default
//                String email = prefs.getString("email", token.email); // Replace with actual default
//
//                response.setDevEui(devEui);
//                response.setEmail(email);
//                response.setOrder_id(paymentData.getOrderId());
//                response.setId(razorpayPaymentId);
//                response.setRazorpay_signature(paymentData.getSignature());
//
//                // Create JSON object with only required fields
//                JSONObject logJson = new JSONObject();
//                logJson.put("devEui", response.getDevEui());
//                logJson.put("email", response.getEmail());
//                logJson.put("razorpay_order_id", response.getOrder_id());
//                logJson.put("razorpay_payment_id", response.getId());
//                logJson.put("razorpay_signature", response.getRazorpay_signature());
//
//                String prettyJson = logJson.toString(4);
////                Log.d(TAG, "Filtered Payment Data: " + prettyJson);
//
//                if (retu != null) {
//                    retu.setText(prettyJson);
//                }
//
//                // Send only required data to the server
//                sendPaymentToServerWithDelays(response);
//            } catch (Exception e) {
//                Log.e(TAG, "Error formatting payment data", e);
//                Toast.makeText(RechargeActivity.this,
//                        "Error processing payment: " + e.getMessage(),
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void sendPaymentToServerWithDelays(RozarPayResponse response) {
//        Retrofit retrofit = getRetrofitInstance();
//
//        Log.d(TAG, "Waiting for 5 seconds before sending payment details to server at: " + System.currentTimeMillis());
//        Toast.makeText(RechargeActivity.this, "Please wait for 5 seconds before processing payment", Toast.LENGTH_LONG).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            Log.d(TAG, "Completed 5-second wait. Sending payment details to server at: " + System.currentTimeMillis());
//
//            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//
//            // Use HashMap instead of JSONObject
//            Map<String, String> filteredRequest = new HashMap<>();
//            filteredRequest.put("devEui", response.getDevEui());
//            filteredRequest.put("email", response.getEmail());
//            filteredRequest.put("razorpay_order_id", response.getOrder_id());
//            filteredRequest.put("razorpay_payment_id", response.getId());
//            filteredRequest.put("razorpay_signature", response.getRazorpay_signature());
//
//            // Convert to JSON string only for logging (not for Retrofit)
//            Log.d(TAG, "Sending filtered payment details to server: " + new JSONObject(filteredRequest).toString());
//
//            Call<RozarPayResponse> call = requestApi.postPaymentStatus(filteredRequest);
//
//            call.enqueue(new Callback<RozarPayResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<RozarPayResponse> call, @NonNull Response<RozarPayResponse> response) {
//                    if (response.isSuccessful()) {
//                        Log.d(TAG, "Server Response: " + response.body());
//                        Toast.makeText(RechargeActivity.this, "Payment details updated successfully", Toast.LENGTH_LONG).show();
//
//                        Log.d(TAG, "Waiting for 5 seconds after sending payment details at: " + System.currentTimeMillis());
//                        Toast.makeText(RechargeActivity.this, "Please wait for 5 seconds while we process your payment", Toast.LENGTH_LONG).show();
//
//                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                            Log.d(TAG, "Completed 5-second wait after sending details at: " + System.currentTimeMillis());
//                            Toast.makeText(RechargeActivity.this, "Processing complete!", Toast.LENGTH_LONG).show();
//                        }, 5000);
//                    } else {
//                        Log.e(TAG, "Payment status update failed: " + response.code());
//                        Toast.makeText(RechargeActivity.this, "Failed to update payment details", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<RozarPayResponse> call, @NonNull Throwable t) {
//                    Log.e(TAG, "Payment processing error: ", t);
//                    Toast.makeText(RechargeActivity.this, "Error updating payment details: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//        }, 5000); // Decreased delay from 60,000 ms (1 min) to 5,000 ms (5 sec)
//    }
////    /private void postPaymentStatus(PaymentData paymentData) {
////        Retrofit retrofit = getRetrofitInstance();
////
////        // Constructing the response object
////        RozarPayResponse newresponse = new RozarPayResponse();
////        newresponse.setId(paymentData.getPaymentId());
////        newresponse.setOrder_id(paymentData.getOrderId());
////        newresponse.setEntity("payment");
////        newresponse.setAmount(Integer.parseInt(amount.getText().toString()) * 100);
////        newresponse.setCurrency("INR");
////        newresponse.setStatus("authorized");
////        newresponse.setMethod("upi");
////        newresponse.setInternational(false);
////        newresponse.setAmount_refunded(0);
////        newresponse.setCaptured(false);
////        newresponse.setDescription("Meter Billing");
////        newresponse.setEmail(email.getText().toString());
////        newresponse.setContact(phone.getText().toString());
////
////        // Notes section (optional)
////        RozarPayResponse.Notes notes = new RozarPayResponse.Notes();
////        notes.setShipping_address("SSTPL sodala, Jaipur");
////        newresponse.setNotes(notes);
////
////        // Log before first delay
////        Log.d(TAG, "Waiting for 1 minute before sending payment details to server at: " + System.currentTimeMillis());
////        Toast.makeText(RechargeActivity.this, "Please wait for 1 minute before processing payment", Toast.LENGTH_LONG).show();
////
////        // Delay for 1 minute before sending the request
////        new Handler(Looper.getMainLooper()).postDelayed(() -> {
////            // Log after waiting for 1 minute
////            Log.d(TAG, "Completed 1-minute wait period. Sending payment details to server at: " + System.currentTimeMillis());
////
////            // API call
////            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
////            Call<RozarPayResponse> call = requestApi.postPaymentStatus(newresponse);
////
////            Log.d(TAG, "Sending payment details to server: " + newresponse.toString());
////
////            call.enqueue(new Callback<RozarPayResponse>() {
////                @Override
////                public void onResponse(@NonNull Call<RozarPayResponse> call, @NonNull Response<RozarPayResponse> response) {
////                    if (response.isSuccessful()) {
////                        Log.d(TAG, "Server Response: " + response.body());
////                        Toast.makeText(RechargeActivity.this, "Payment details updated successfully", Toast.LENGTH_LONG).show();
////
////                        // Log before second delay
////                        Log.d(TAG, "Waiting for 1 minute after sending payment details at: " + System.currentTimeMillis());
////                        Toast.makeText(RechargeActivity.this, "Please wait for 1 minute while we process your payment", Toast.LENGTH_LONG).show();
////
////                        // Delay for 1 minute after sending the request
////                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
////                            // Log after waiting for 1 minute
////                            Log.d(TAG, "Completed 1-minute wait period after sending details at: " + System.currentTimeMillis());
////                            Toast.makeText(RechargeActivity.this, "Processing complete!", Toast.LENGTH_LONG).show();
////                        }, 60000); // 60 seconds = 1 minute
////                    } else {
////                        Log.e(TAG, "Payment status update failed: " + response.code());
////                        Toast.makeText(RechargeActivity.this, "Failed to update payment details", Toast.LENGTH_LONG).show();
////                    }
////                }
////
////                @Override
////                public void onFailure(@NonNull Call<RozarPayResponse> call, @NonNull Throwable t) {
////                    Log.e(TAG, "Payment status update error: ", t);
////                    Toast.makeText(RechargeActivity.this, "Error updating payment details: " + t.getMessage(), Toast.LENGTH_LONG).show();
////                }
////            });
////
////        }, 60000); // 60 seconds = 1 minute delay before sending the request
////    }
////
//
//    @Override
//    public void onPaymentError(int i, String s, PaymentData paymentData) {
//        Log.e(TAG, "Payment Failed with code: " + i);
//        Log.e(TAG, "Error message: " + s);
//
//        if (paymentData != null) {
//            Log.e(TAG, "Order ID: " + paymentData.getOrderId());
//            Log.e(TAG, "Payment ID: " + paymentData.getPaymentId());
//            Log.e(TAG, "Signature: " + paymentData.getSignature());
//        } else {
//            Log.e(TAG, "PaymentData is null");
//        }
//
//        rozarPayPaymentCode = i;
//        rozarPayPaymentId += s;
//
//        retu.setText(i + "\n" +
//                rozarPayPaymentId + "\n" +
//                (paymentData != null ? paymentData.getPaymentId() : "null") + "\n" +
//                (paymentData != null ? paymentData.getOrderId() : "null") + "\n" +
//                (paymentData != null ? paymentData.getSignature() : "null"));
//    }
//    private void rozroPayApi() {
//        Retrofit retrofit = getRetrofitInstance();
//
//        // Create payment model with required data
//        PayModel payModel = new PayModel(
//                email.getText().toString(),
//                amount.getText().toString(),
//                phone.getText().toString(),
//                householdId
//        );
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<RzResponse> call = requestApi.getMeterRechargeApi(payModel);
//
//        Log.d(TAG, "Calling recharge API: " + call.request().url());
//
//        call.enqueue(new Callback<RzResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<RzResponse> call, @NonNull Response<RzResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "Recharge API call successful");
//                    RzResponse object = response.body();
//                    rzResponse = object;
//
//                    // Check if we have the required data
//                    if (object.getID() != null && object.getSkey() != null) {
//                        // Start payment with received order ID and key
//                        startPayment(object.getID(), object.getSkey());
//                    } else {
//                        Toast.makeText(RechargeActivity.this,
//                                "Invalid response from server",
//                                Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Log.e(TAG, "Recharge API call failed: " + response.code() + " - " + response.message());
//                    Toast.makeText(RechargeActivity.this,
//                            "Error: " + response.code() + " - " + response.message(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RzResponse> call, @NonNull Throwable t) {
//                Toast.makeText(RechargeActivity.this,
//                        "Connection failed: " + t.getMessage(),
//                        Toast.LENGTH_LONG).show();
//                Log.e(TAG, "API Error: ", t);
//            }
//        });
//    }
//
//    private void rozarPayPaymentStatusApi(String razorpay_order_id, String razorpay_payment_id, String razorpay_signature) {
//        // Log the request parameters
//        Log.d(TAG, "Calling status API with params: " +
//                "\nOrder ID: " + razorpay_order_id +
//                "\nPayment ID: " + razorpay_payment_id +
//                "\nSignature: " + razorpay_signature +
//                "\nEmail: " + eml +
//                "\nHousehold ID: " + householdId);
//
//        Retrofit retrofit = getRetrofitInstance();
//
//        // Create the request body
//        RzPayStatus rzPayStatus = new RzPayStatus(
//                eml,
//                razorpay_order_id,
//                razorpay_payment_id,
//                householdId,
//                razorpay_signature
//        );
//
//        // Log the full request object
//        Log.d(TAG, "Request body: " + rzPayStatus.toString());
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<RzStatusResponse> call = requestApi.getRzPyStatus(rzPayStatus);
//
//        // Log the full request URL and headers
//        Log.d(TAG, "Request URL: " + call.request().url());
//        Log.d(TAG, "Request Headers: " + call.request().headers());
//
//        call.enqueue(new Callback<RzStatusResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<RzStatusResponse> call, @NonNull Response<RzStatusResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    RzStatusResponse rzStatusResponse = response.body();
//                    Log.d(TAG, "Payment status API success: " + rzStatusResponse.getDetail());
//                    Toast.makeText(RechargeActivity.this,
//                            "Payment Status: " + rzStatusResponse.getDetail(),
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    try {
//                        // Try to get error body
//                        String errorBody = response.errorBody() != null ?
//                                response.errorBody().string() : "No error body";
//                        Log.e(TAG, "Payment status API failed: " +
//                                "\nCode: " + response.code() +
//                                "\nMessage: " + response.message() +
//                                "\nError Body: " + errorBody);
//
//                        Toast.makeText(RechargeActivity.this,
//                                "Payment status check failed: " + response.code() +
//                                        " - " + response.message(),
//                                Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Log.e(TAG, "Error reading error body", e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RzStatusResponse> call, @NonNull Throwable t) {
//                Log.e(TAG, "Payment status API connection error", t);
//
//                String errorMessage = "Connection error: ";
//                if (t instanceof java.net.SocketTimeoutException) {
//                    errorMessage += "Request timed out";
//                } else if (t instanceof java.net.UnknownHostException) {
//                    errorMessage += "Cannot reach server";
//                } else {
//                    errorMessage += t.getMessage();
//                }
//
//                Toast.makeText(RechargeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//}


package com.example.siotel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.PayModel;
import com.example.siotel.models.RozarPayResponse;
import com.example.siotel.models.RzPayStatus;
import com.example.siotel.models.RzResponse;
import com.example.siotel.models.RzStatusResponse;
import com.example.siotel.models.Token;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RechargeActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    SharedPrefManager sharedPrefManager;
    Token token;
    String eml, phn;

    RzResponse rzResponse;
    JSONObject js = new JSONObject();

    EditText amount, phone;
    TextView email, hid, retu;

    Button button;

    String householdId = "";
    PaymentData pd;
    String rozarPayPaymentId = "";
    int rozarPayPaymentCode = 0;
    LottieAnimationView lottieAnimationView;

    private static final String TAG = RechargeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.playAnimation();

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> lottieAnimationView.setProgress((Float) animation.getAnimatedValue()));
        animator.start();

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        token = sharedPrefManager.getUser();

        rzResponse = new RzResponse();

        Intent intent = getIntent();
        householdId += intent.getStringExtra("Hid");
        email = findViewById(R.id.remail);
        amount = findViewById(R.id.ramount);
        phone = findViewById(R.id.rphone);
        hid = findViewById(R.id.rhid);
        button = findViewById(R.id.rrecharge);
        retu = findViewById(R.id.retu);

        phn = phone.getText().toString();
        eml = token.getEmail();
        email.setText(eml);
        hid.setText(householdId);

        onClick();
    }

    private Retrofit getRetrofitInstance() {
        // Add HTTP logging interceptor for detailed request/response logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(logging) // Add logging interceptor
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void onClick() {
        button.setOnClickListener(view -> {
            if (TextUtils.isEmpty(amount.getText())) {
                amount.setError("Please enter amount");
            } else if (TextUtils.isEmpty(phone.getText())) {
                phone.setError("Please enter phone number");
            } else if (TextUtils.isEmpty(email.getText())) {
                email.setError("Please enter email");
            } else {
                Checkout.preload(getApplicationContext());
                rozroPayApi();
            }
        });
    }

    public void startPayment(String orderId, String key) {
        int intAmt = Math.round(Float.parseFloat(amount.getText().toString())) * 100;
        Checkout checkout = new Checkout();
        checkout.setKeyID(key);
        checkout.setImage(R.drawable.gr1);

        final FragmentActivity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", token.getEmail());
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", intAmt);
            options.put("prefill.email", eml);
            options.put("prefill.contact", phone.getText().toString());
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error starting payment: ", e);
            Toast.makeText(RechargeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
        if (paymentData != null) {
            try {
                RozarPayResponse response = new RozarPayResponse();
                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                String devEui = prefs.getString("devEui", householdId);
                String email = prefs.getString("email", token.getEmail());

                response.setDevEui(devEui);
                response.setEmail(email);
                response.setOrder_id(paymentData.getOrderId());
                response.setId(razorpayPaymentId);
                response.setRazorpay_signature(paymentData.getSignature());

                JSONObject logJson = new JSONObject();
                logJson.put("devEui", response.getDevEui());
                logJson.put("email", response.getEmail());
                logJson.put("razorpay_order_id", response.getOrder_id());
                logJson.put("razorpay_payment_id", response.getId());
                logJson.put("razorpay_signature", response.getRazorpay_signature());

                String prettyJson = logJson.toString(4);
                if (retu != null) {
                    retu.setText(prettyJson);
                }

                sendPaymentToServerWithDelays(response);
            } catch (Exception e) {
                Log.e(TAG, "Error formatting payment data", e);
                Toast.makeText(RechargeActivity.this, "Error processing payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendPaymentToServerWithDelays(RozarPayResponse response) {
        Retrofit retrofit = getRetrofitInstance();

        Log.d(TAG, "Waiting 5 seconds before sending payment details at: " + System.currentTimeMillis());
        Toast.makeText(RechargeActivity.this, "Please wait 5 seconds before processing payment", Toast.LENGTH_LONG).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Log.d(TAG, "Sending payment details after 5-second delay at: " + System.currentTimeMillis());

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Map<String, String> filteredRequest = new HashMap<>();
            filteredRequest.put("devEui", response.getDevEui());
            filteredRequest.put("email", response.getEmail());
            filteredRequest.put("razorpay_order_id", response.getOrder_id());
            filteredRequest.put("razorpay_payment_id", response.getId());
            filteredRequest.put("razorpay_signature", response.getRazorpay_signature());

            Log.d(TAG, "Sending filtered payment details: " + new JSONObject(filteredRequest).toString());

            Call<RozarPayResponse> call = requestApi.postPaymentStatus(filteredRequest);

            call.enqueue(new Callback<RozarPayResponse>() {
                @Override
                public void onResponse(@NonNull Call<RozarPayResponse> call, @NonNull Response<RozarPayResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Server Response: " + response.body());
                        Toast.makeText(RechargeActivity.this, "Payment details updated successfully", Toast.LENGTH_LONG).show();

                        Log.d(TAG, "Waiting 5 seconds after sending details at: " + System.currentTimeMillis());
                        Toast.makeText(RechargeActivity.this, "Please wait 5 seconds while we process your payment", Toast.LENGTH_LONG).show();

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Log.d(TAG, "Processing complete at: " + System.currentTimeMillis());
                            Toast.makeText(RechargeActivity.this, "Processing complete!", Toast.LENGTH_LONG).show();
                        }, 5000);
                    } else {
                        Log.e(TAG, "Payment status update failed: " + response.code());
                        Toast.makeText(RechargeActivity.this, "Failed to update payment details", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RozarPayResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "Payment processing error: ", t);
                    Toast.makeText(RechargeActivity.this, "Error updating payment details: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }, 5000);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e(TAG, "Payment Failed with code: " + i + ", message: " + s);
        if (paymentData != null) {
            Log.e(TAG, "Order ID: " + paymentData.getOrderId() + ", Payment ID: " + paymentData.getPaymentId() + ", Signature: " + paymentData.getSignature());
        } else {
            Log.e(TAG, "PaymentData is null");
        }

        rozarPayPaymentCode = i;
        rozarPayPaymentId += s;
        retu.setText(i + "\n" + rozarPayPaymentId + "\n" +
                (paymentData != null ? paymentData.getPaymentId() : "null") + "\n" +
                (paymentData != null ? paymentData.getOrderId() : "null") + "\n" +
                (paymentData != null ? paymentData.getSignature() : "null"));
    }

    private void rozroPayApi() {
        Retrofit retrofit = getRetrofitInstance();

        // Validate token before proceeding
        if (token == null || token.getEmail() == null) {
            Toast.makeText(RechargeActivity.this, "Authentication token is missing. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        // Create payment model with required data
        PayModel payModel = new PayModel(
                email.getText().toString(),
                amount.getText().toString(),
                phone.getText().toString(),
                householdId
        );

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<RzResponse> call = requestApi.getMeterRechargeApi(payModel);

        // Log request details
        Log.d(TAG, "Request URL: " + call.request().url());
        Log.d(TAG, "Request Body: " + payModel.toString()); // Ensure PayModel has a toString() method

        call.enqueue(new Callback<RzResponse>() {
            @Override
            public void onResponse(@NonNull Call<RzResponse> call, @NonNull Response<RzResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Recharge API call successful: " + response.body().toString());
                    RzResponse object = response.body();
                    rzResponse = object;

                    if (object.getID() != null && object.getSkey() != null) {
                        startPayment(object.getID(), object.getSkey());
                    } else {
                        Log.e(TAG, "Invalid response: Missing ID or Skey");
                        Toast.makeText(RechargeActivity.this, "Invalid response from server", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Log detailed error response
                    String errorBody = "No error body";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                    Log.e(TAG, "Recharge API failed: Code=" + response.code() + ", Message=" + response.message() + ", Error Body=" + errorBody);
                    Toast.makeText(RechargeActivity.this, "Error: " + response.code() + " - " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RzResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "API Failure: " + t.getMessage(), t);
                Toast.makeText(RechargeActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void rozarPayPaymentStatusApi(String razorpay_order_id, String razorpay_payment_id, String razorpay_signature) {
        // Implementation remains unchanged for brevity
        // Add similar logging and error handling if needed

        // Log the request parameters
        Log.d(TAG, "Calling status API with params: " +
                "\nOrder ID: " + razorpay_order_id +
                "\nPayment ID: " + razorpay_payment_id +
                "\nSignature: " + razorpay_signature +
                "\nEmail: " + eml +
                "\nHousehold ID: " + householdId);

        Retrofit retrofit = getRetrofitInstance();

        // Create the request body
        RzPayStatus rzPayStatus = new RzPayStatus(
                eml,
                razorpay_order_id,
                razorpay_payment_id,
                householdId,
                razorpay_signature
        );

        // Log the full request object
        Log.d(TAG, "Request body: " + rzPayStatus.toString());

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<RzStatusResponse> call = requestApi.getRzPyStatus(rzPayStatus);

        // Log the full request URL and headers
        Log.d(TAG, "Request URL: " + call.request().url());
        Log.d(TAG, "Request Headers: " + call.request().headers());

        call.enqueue(new Callback<RzStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<RzStatusResponse> call, @NonNull Response<RzStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RzStatusResponse rzStatusResponse = response.body();
                    Log.d(TAG, "Payment status API success: " + rzStatusResponse.getDetail());
                    Toast.makeText(RechargeActivity.this,
                            "Payment Status: " + rzStatusResponse.getDetail(),
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        // Try to get error body
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "No error body";
                        Log.e(TAG, "Payment status API failed: " +
                                "\nCode: " + response.code() +
                                "\nMessage: " + response.message() +
                                "\nError Body: " + errorBody);

                        Toast.makeText(RechargeActivity.this,
                                "Payment status check failed: " + response.code() +
                                        " - " + response.message(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RzStatusResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Payment status API connection error", t);

                String errorMessage = "Connection error: ";
                if (t instanceof java.net.SocketTimeoutException) {
                    errorMessage += "Request timed out";
                } else if (t instanceof java.net.UnknownHostException) {
                    errorMessage += "Cannot reach server";
                } else {
                    errorMessage += t.getMessage();
                }

                Toast.makeText(RechargeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}