package com.example.siotel.fragment;

import static java.time.LocalDateTime.of;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.adapters.HouseHoldsAdapter;
import com.example.siotel.adapters.HouseholdsDetailsAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.StringToDoubleStorage;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.MyDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HouseholdsDetailsFragment extends Fragment{

    List<HouseholdsDetailsModel> arrayList;
    HouseholdsDetailsAdapter householdsDetailsAdapter;
    RecyclerView householdsDetailsRv;
   SharedPrefManager sharedPrefManager;
    MyDatabase myDatabase;
    static String householdId="";
    public HouseholdsDetailsFragment(String toString) {
        householdId=toString;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_households_details, container, false);
        findById(v);
        getHouseholdDetailsFromDB(householdId);
        return v;
    }

    private void findById(View v)
    {
        householdsDetailsRv=v.findViewById(R.id.households_details_hori_rv);
        sharedPrefManager=new SharedPrefManager(getContext());
        myDatabase=new MyDatabase(getContext());
    }
    private void getHouseholdDetailsFromDB(String meterno)
    {
        callHouseholdDetailsApi();

      /*  LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        householdsDetailsRv.setLayoutManager(llm);
        arrayList=new ArrayList<>();
        arrayList=myDatabase.getHouseholdsDetails(meterno);
        List<HouseholdsDetailsModel> dlist2=new ArrayList<>();
        dlist2.add(new HouseholdsDetailsModel("Household Number",0,0,"Date andTtime"));
        dlist2.addAll(arrayList);
        householdsDetailsAdapter=new HouseholdsDetailsAdapter(dlist2);
        householdsDetailsRv.setAdapter(householdsDetailsAdapter);*/


    }
    private void callHouseholdDetailsApi() {

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        householdsDetailsRv.setLayoutManager(llm);

        // Retrieve the token
        String token = sharedPrefManager.getAccessToken();
        if (token == null) {
            Log.e("callHouseholdApi", "Token is null!");
            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }
        String tokenstr = "Bearer " + token;

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        String url = "MeterdataApi/" + householdId;


        Toast.makeText(getContext(), url + " " + token, Toast.LENGTH_LONG).show();

        // API call
        Call<List<HouseholdsDetailsModel>> call = requestApi.getMetersDtl(url, tokenstr);

        call.enqueue(new Callback<List<HouseholdsDetailsModel>>() {
           // @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<List<HouseholdsDetailsModel>> call, @NonNull Response<List<HouseholdsDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HouseholdsDetailsModel> responselist = response.body();

                    // Logging the response for debugging
                    Log.d("HouseholdDetails", "Response received: " + responselist);

                 /*  if (myDatabase.getHouseholdDetailsCount(householdId) == 0) {
                        for (HouseholdsDetailsModel d : responselist) {
                            myDatabase.addHouseHoldDetails(d.getMeterSN(), String.valueOf(d.getCum_eb_kwh()), String.valueOf(d.getBalance_amount()), d.getDate());
                        }
                    } else {
                        List<HouseholdsDetailsModel> beforeList = myDatabase.getHouseholdsDetails(householdId);
                        String beforeTimeString = beforeList.get(0).getDate();
                        String beforeTimeArray[] = beforeTimeString.split("[-,T,:,.]");

                        LocalDateTime beforeDate = LocalDateTime.of(
                                Integer.parseInt(beforeTimeArray[0]),
                                Integer.parseInt(beforeTimeArray[1]),
                                Integer.parseInt(beforeTimeArray[2]),
                                Integer.parseInt(beforeTimeArray[3]),
                                Integer.parseInt(beforeTimeArray[4]),
                                Integer.parseInt(beforeTimeArray[5])
                        );

                        for (HouseholdsDetailsModel d : responselist) {
                            String afterTimeString = d.getDate();
                            String afterTimeArray[] = afterTimeString.split("[-,T,:,.]");
                            LocalDateTime afterDate = LocalDateTime.of(
                                    Integer.parseInt(afterTimeArray[0]),
                                    Integer.parseInt(afterTimeArray[1]),
                                    Integer.parseInt(afterTimeArray[2]),
                                    Integer.parseInt(afterTimeArray[3]),
                                    Integer.parseInt(afterTimeArray[4]),
                                    Integer.parseInt(afterTimeArray[5])
                            );

                            if (afterDate.isAfter(beforeDate)) {
                                myDatabase.addHouseHoldDetails(d.getMeterSN(), String.valueOf(d.getCum_eb_kwh()), String.valueOf(d.getBalance_amount()), d.getDate());
                            }
                        }
                    } */
                   // arrayList=new ArrayList<>();
                    //arrayList=myDatabase.getHouseholdsDetails(meterno);
                    List<HouseholdsDetailsModel> dlist2=new ArrayList<>();
                    String cum_eb_kwh ="cum_eb_kwh";
                    String balance_amount="balance amount";
                    double dcum_eb_kwh = StringToDoubleStorage.stringToDouble(cum_eb_kwh);
                    double dbalance_amount = StringToDoubleStorage.stringToDouble(balance_amount);

                    dlist2.add(new HouseholdsDetailsModel("Household Number",dcum_eb_kwh,dbalance_amount,"Date andTtime"));
                    dlist2.addAll(responselist);
                    householdsDetailsAdapter=new HouseholdsDetailsAdapter(dlist2);
                    householdsDetailsRv.setAdapter(householdsDetailsAdapter);

                } else {
                    // Log and display error details
                    Log.e("API Response Error", "Code: " + response.code() + ", Message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("Error Body", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(getContext(), "Household details response not successful.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HouseholdsDetailsModel>> call, @NonNull Throwable t) {
                // Log and display failure details
                Log.e("API Call Failure", t.toString());
                Toast.makeText(getContext(), "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}