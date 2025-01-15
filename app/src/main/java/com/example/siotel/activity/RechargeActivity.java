package com.example.siotel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.siotel.interfaces.HouseHoldId;
import com.example.siotel.models.PayModel;
import com.example.siotel.models.RozarPayResponse;
import com.example.siotel.models.RzPayStatus;
import com.example.siotel.models.RzResponse;
import com.example.siotel.models.RzStatusResponse;
import com.example.siotel.models.Token;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RechargeActivity extends AppCompatActivity  implements PaymentResultWithDataListener {




    SharedPrefManager sharedPrefManager;
    Token token;
    String eml,phn;

    RzResponse rzResponse;
    JSONObject js=new JSONObject();


    EditText amount,phone;
    TextView email,hid,retu;

    Button button;

    String householdId="";

    PaymentData pd;
    String rozarPayPaymentId="";
    int    rozarPayPaymentCode=0;
    LottieAnimationView lottieAnimationView;

    private static final String TAG = RechargeActivity.class.getSimpleName();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Payment");

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

      //  getSupportActionBar().setTitle("Payment");


        lottieAnimationView=findViewById(R.id.animation_view);

        lottieAnimationView.addAnimatorUpdateListener((animation) -> {
            // Do something.
        });
        lottieAnimationView.playAnimation();

        if (lottieAnimationView.isAnimating()) {
            // Do something.
        }

        // Custom animation speed or duration.
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {lottieAnimationView.setProgress((Float) animation.getAnimatedValue());});
        animator.start();




        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        token=sharedPrefManager.getUser();


          rzResponse=new RzResponse();



        Intent intent=getIntent();

        householdId+=intent.getStringExtra("Hid");
        email=findViewById(R.id.remail);
        amount=findViewById(R.id.ramount);
        phone=findViewById(R.id.rphone);
        hid=findViewById(R.id.rhid);
        button=findViewById(R.id.rrecharge);
        retu=findViewById(R.id.retu);

        phn=phone.getText().toString();
        eml=token.getEmail();
        email.setText(eml);
        hid.setText(householdId);




        onClick();







    }
    public void onClick()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(amount.getText()))
                    amount.setError("please enter amount");
                else if(TextUtils.isEmpty(phone.getText()))
                    phone.setError("please enter phone number");
                else {
                    Checkout.preload(getApplicationContext());
                    rozroPayApi();
                    startPayment();
                }



            }
        });
    }

    public void startPayment() {



         int intAmt=Math.round(Float.parseFloat(amount.getText().toString()))*100;
        Checkout checkout = new Checkout();

        checkout.setKeyID(rzResponse.getSkey());


        checkout.setImage(R.drawable.gr1);


        final FragmentActivity activity =this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", token.getEmail());
            //   options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", rzResponse.getID());//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", intAmt);//pass amount in currency subunits
            options.put("prefill.email", "mukesh61112@gmail.com");
            options.put("prefill.contact","9116827161");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.v(TAG, e.toString());
            Toast.makeText(RechargeActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            retu.setText(e.toString());

        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        rozarPayPaymentStatusApi(paymentData.getOrderId(),paymentData.getPaymentId(),paymentData.getSignature());


        rozarPayPaymentId+=s;

        retu.setText(rozarPayPaymentId+"\n"+  paymentData.getPaymentId()+"\n"+paymentData.getOrderId()+"\n"+paymentData.getSignature());

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
            rozarPayPaymentCode=i;
            rozarPayPaymentId+=s;

        retu.setText(Integer.toString(i)+"\n"+ rozarPayPaymentId+"\n"+  paymentData.getPaymentId()+"\n"+paymentData.getOrderId()+"\n"+paymentData.getSignature());

    }
    private void rozroPayApi()
    {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            PayModel payModel = new PayModel(eml, amount.getText().toString(), phone.getText().toString(), householdId);


            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

            Call<RzResponse> call = requestApi.getRzPay(payModel);

            call.enqueue(new Callback<RzResponse>() {

                @Override
                public void onResponse(@NonNull Call<RzResponse> call, @NonNull Response<RzResponse> response) {
                    if (response.isSuccessful()) {

                        Log.v("haha", " have no error");
                        RzResponse object = response.body();
                        rzResponse = response.body();
                        Toast.makeText(RechargeActivity.this, object.getID() + "   " + object.getSkey(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(g,object.getResponseObject().getCurrency().toString(),Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(RechargeActivity.this, " rozro pay me kuch grbr h ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RzResponse> call, Throwable t) {
                    Toast.makeText(RechargeActivity.this, " rozro pay api connect nahi hio", Toast.LENGTH_LONG).show();
                    Log.v("err", t.toString());
                }
            });

    }
    private void rozarPayPaymentStatusApi( String razorpay_order_id, String razorpay_payment_id,  String razorpay_signature)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RzPayStatus rzPayStatus=new RzPayStatus(eml,razorpay_order_id,razorpay_payment_id,householdId,razorpay_signature);


        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        Call<RzStatusResponse> call=requestApi.getRzPyStatus(rzPayStatus);

        call.enqueue(new Callback<RzStatusResponse>() {

            @Override
            public void onResponse(@NonNull Call<RzStatusResponse> call, @NonNull Response<RzStatusResponse> response) {
                if (response.isSuccessful()) {

                    RzStatusResponse rzStatusResponse=response.body();
                    Log.v("stats",rzStatusResponse.getDetail());
                    Toast.makeText(RechargeActivity.this,rzStatusResponse.getDetail(),Toast.LENGTH_LONG).show();


                }
                else
                {
                    Log.v("rps",response.message()+"  "+response.code()+" ");

                    Toast.makeText(RechargeActivity.this," rozro pay payment status nahi mila h ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RzStatusResponse> call, Throwable t) {
                Toast.makeText(RechargeActivity.this," rozro pay payment status Api connect nahi hui",Toast.LENGTH_LONG).show();
                Log.v("err",t.toString());
            }
        });

    }

}