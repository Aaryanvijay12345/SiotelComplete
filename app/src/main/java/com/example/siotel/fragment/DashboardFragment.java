package com.example.siotel.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.adapters.IotIntroAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.IotIntroModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.HouseholdDatabase;
import com.example.siotel.sqlitedatabase.MyDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DashboardFragment extends Fragment {


    RecyclerView sliderView;
    TextView setHouseholdCount,totalRecharge;
    RecyclerView recyclerView;
    List<IotIntroModel> list;
    IotIntroAdapter iotIntroAdapter;
    SharedPrefManager sharedPrefManager;
    HouseholdDatabase householdDatabase;
    MyDatabase myDatabase;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    private int scrollPosition = 0;
    private int scrollDelay = 3000; // 3 seconds for each scroll
    private Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_dashboard, container, false);
        findViewById(v);
        startAutoScrolling();
           setHouseholdCount();
           setTotalRecharge();
        return v;
    }
    private void findViewById(View v)
    {

      //  sliderView=v.findViewById(R.id.introRv);
        setHouseholdCount=v.findViewById(R.id.householdCount);
        sharedPrefManager=new SharedPrefManager(getContext());
        householdDatabase=new HouseholdDatabase(getContext());
        totalRecharge=v.findViewById(R.id.totalRecharge);
        myDatabase=new MyDatabase(getContext());
        horizontalScrollView = v.findViewById(R.id.horizontalScrollView);
        linearLayout = v.findViewById(R.id.scrollContent);
    }
    private void startAutoScrolling() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int childCount = linearLayout.getChildCount();
                if (childCount > 0) {
                    // Scroll horizontally by the width of the next child
                    scrollPosition += linearLayout.getChildAt(0).getWidth();

                    // If the scroll position exceeds the total width, reset it
                    if (scrollPosition >= linearLayout.getWidth()) {
                        scrollPosition = 0;
                    }

                    horizontalScrollView.smoothScrollTo(scrollPosition, 0);
                }

                // Call again after a delay
                handler.postDelayed(this, scrollDelay);
            }
        };

        // Start the scrolling
        handler.post(runnable);
    }
    private void iotIntroRV() {
        list = new ArrayList<IotIntroModel>();
        sliderView.setLayoutDirection(RecyclerView.LAYOUT_DIRECTION_LTR);
        list.add(new IotIntroModel(R.drawable.iot4));
        list.add(new IotIntroModel(R.drawable.iot1));
        list.add(new IotIntroModel(R.drawable.iot2));
        list.add(new IotIntroModel(R.drawable.iot3));
        iotIntroAdapter = new IotIntroAdapter(getContext(), list);
        sliderView.setAdapter(iotIntroAdapter);
    }
    private void setHouseholdCount()
    {
        int count=myDatabase.getHouseholdCount();
        setHouseholdCount.setText( Integer.toString(count));
        callHouseholdApi();


    }
    private void setTotalRecharge()
    {
        int tr=myDatabase.getRechargeDB();
        totalRecharge.setText(Integer.toString(tr));
        callTotalRechargeApi();
    }

    private void callHouseholdApi() {
        // Step 1: Check if token is null
        String token = sharedPrefManager.getAccessToken();
        String email = sharedPrefManager.getEmail();
        if (token == null) {
            Log.e("callHouseholdApi", "Token is null!");
            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String tokenstr = "Bearer " + token;
        if (email == null || email.isEmpty()) {
            Log.e("callHouseholdApi", "Email is null or empty!");
            Toast.makeText(getContext(), "Email not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        SaveEmail saveEmail = new SaveEmail(email);

        // Step 2: Retrofit setup and API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<List<HouseholdsModel>> call = requestApi.getAllMeter(tokenstr, saveEmail);

        // Step 3: Enqueue API call
        call.enqueue(new Callback<List<HouseholdsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
                if (response.isSuccessful()) {
                    List<HouseholdsModel> dlist = response.body();

                    // Null check for response body
                    if (dlist != null) {
                        if (myDatabase.getHouseholdCount() < dlist.size()) {
                            int oldCount = myDatabase.getHouseholdCount();
                            int newCount = dlist.size();

                            for (int i = oldCount; i < newCount; i++) {
                                HouseholdsModel household = dlist.get(i);
                                String date = household.getDate();

                                if (date != null) {
                                    String[] arr = date.split("[T]");
                                    if (arr.length > 0) {
                                        myDatabase.addHouseHold(household.getHouseholdname(), household.getMetersno(), arr[0]);
                                    } else {
                                        Log.w("callHouseholdApi", "Invalid date format for household " + household.getHouseholdname());
                                    }
                                } else {
                                    Log.w("callHouseholdApi", "Date is null for household " + household.getHouseholdname());
                                }
                            }
                        } else {
                         //   Toast.makeText(getContext(), "Household  is up to date.", Toast.LENGTH_SHORT).show();
                        }
                        int count=myDatabase.getHouseholdCount();
                        setHouseholdCount.setText( Integer.toString(count));
                    }
                    else {
                        Toast.makeText(getContext(), "Response body is null", Toast.LENGTH_SHORT).show();
                        Log.e("callHouseholdApi", "Response body is null.");
                    }
                } else {
                    // Handle unsuccessful response

                    Toast.makeText(getContext(), "Household API response not successful", Toast.LENGTH_LONG).show();
                    Log.e("callHouseholdApi", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<HouseholdsModel>> call, Throwable t) {
                // Log the error and display a toast message
                Log.e("callHouseholdApi", "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callTotalRechargeApi() {
        String token = sharedPrefManager.getAccessToken();
        String email = sharedPrefManager.getEmail();
        if (token == null) {
            Log.e("callHouseholdApi", "Token is null!");
            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String tokenstr = "Bearer " + token;
        if (email == null || email.isEmpty()) {
            Log.e("callHouseholdApi", "Email is null or empty!");
            Toast.makeText(getContext(), "Email not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }
        SaveEmail saveEmail = new SaveEmail(email);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in") // Ensure the base URL ends with '/'
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<Integer> call = requestApi.getTotalRecharge(tokenstr, saveEmail);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int totalrcg = response.body();
                    try {
                        // Thread-safe database operations
                        //Toast.makeText(getContext(), Integer.toString(totalrcg), Toast.LENGTH_LONG).show();

                        int currentRecharge = myDatabase.getRechargeDB();
                        if (currentRecharge == 0) {
                            myDatabase.addRecharge(totalrcg);
                        } else if (currentRecharge != totalrcg) {
                            myDatabase.updateRecharge(totalrcg);
                        }
                     //   Toast.makeText(getContext(),"Total Recharge Up to date", Toast.LENGTH_LONG).show();
                        int tr=myDatabase.getRechargeDB();
                        totalRecharge.setText(Integer.toString(tr));

                    } catch (Exception e) {
                        Log.e("DatabaseError", "Error updating database", e);
                    }
                } else {
                    try {
                        // Log the error response body for debugging
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e("APIError", "Response Code: " + response.code() + ", Error: " + errorMessage);
                    } catch (IOException e) {
                        Log.e("APIError", "Error parsing error body", e);
                    }
                    Toast.makeText(getContext(), "Error fetching total  recharge.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Log.e("NetworkError", t.toString());
                Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
    }
