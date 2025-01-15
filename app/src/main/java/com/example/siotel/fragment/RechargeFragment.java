package com.example.siotel.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.activity.MainActivity;
import com.example.siotel.activity.RechargeActivity;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.PayModel;
import com.example.siotel.models.RozarPayResponse;
import com.example.siotel.models.RzResponse;
import com.example.siotel.models.Token;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RechargeFragment extends Fragment implements PaymentResultWithDataListener {

    LottieAnimationView lottieAnimationView;
    SharedPrefManager sharedPrefManager;
    Token token;
    String eml,phn;

    RzResponse rzResponse;
    JSONObject js=new JSONObject();


    EditText amount,phone;
    TextView email,hid,retu;

    Button button;

    String householdId;
    private static final String TAG = MainActivity.class.getSimpleName();
    public    RechargeFragment(String householdId)
    {
        this.householdId=householdId;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recharge, container, false);


        lottieAnimationView=view.findViewById(R.id.animation_view);

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





        sharedPrefManager=new SharedPrefManager(getContext());
        token=sharedPrefManager.getUser();
          rzResponse=new RzResponse();
        Checkout.preload(getContext());


        email=view.findViewById(R.id.remail);
        amount=view.findViewById(R.id.ramount);
        phone=view.findViewById(R.id.rphone);
        hid=view.findViewById(R.id.rhid);
        button=view.findViewById(R.id.rrecharge);
        retu=view.findViewById(R.id.retu);

        eml=token.getEmail();
        phn=phone.getText().toString();
        email.setText(eml);
        hid.setText(householdId);




        onClick();











        return view;
    }

    public void onClick()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int intAmt=Math.round(Float.parseFloat(amount.getText().toString()));
                 rozroPayApi();
                  startPayment();


            }
        });
    }

    public void startPayment() {

    int intAmt=Math.round(Float.parseFloat(amount.getText().toString()))*100;
    Checkout checkout = new Checkout();

        checkout.setKeyID(rzResponse.getSkey());


        checkout.setImage(R.drawable.gr1);


    final FragmentActivity activity =getActivity();


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
        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        retu.setText(e.toString());

    }
}
    private void rozroPayApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PayModel payModel=new PayModel(eml,amount.getText().toString(),phone.getText().toString(),householdId);


        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        Call<RzResponse> call=requestApi.getRzPay(payModel);

        call.enqueue(new Callback<RzResponse>() {

            @Override
            public void onResponse(@NonNull Call<RzResponse> call, @NonNull Response<RzResponse> response) {
                if (response.isSuccessful()) {

                    Log.v("haha"," have no error");
                    RzResponse object = response.body();
                    rzResponse=response.body();
                    Toast.makeText(getContext(),object.getID()+"   "+object.getSkey(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(g,object.getResponseObject().getCurrency().toString(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getContext()," rozro pay me kuch grbr h ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RzResponse> call, Throwable t) {
                Toast.makeText(getContext()," rozro pay api connect nahi hio",Toast.LENGTH_LONG).show();
                Log.v("err",t.toString());
            }
        });

    }



    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {


        retu.setText(s+"\n"+  paymentData.getPaymentId()+"\n"+paymentData.getOrderId()+"\n"+paymentData.getSignature());

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {


        retu.setText(Integer.toString(i)+"\n"+ s+"\n"+  paymentData.getPaymentId()+"\n"+paymentData.getOrderId()+"\n"+paymentData.getSignature());

    }
}