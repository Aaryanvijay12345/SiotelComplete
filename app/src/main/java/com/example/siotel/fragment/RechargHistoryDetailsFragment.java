package com.example.siotel.fragment;

import static java.time.LocalDateTime.of;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.adapters.HouseholdsDetailsAdapter;
import com.example.siotel.adapters.RechargeHisDetailsAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HRHDetailsModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.StringToDoubleStorage;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.MyDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RechargHistoryDetailsFragment extends Fragment  {


    SharedPrefManager sharedPrefManager;
    MyDatabase myDatabase;
    String meterno=null;
    public RechargHistoryDetailsFragment(String meterno) {

        this.meterno=meterno;
    }
    RecyclerView hrhdRV;
    List<HRHDetailsModel> arrlist;
    RechargeHisDetailsAdapter rhdadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recharg_history_details, container, false);
        hrhdRV=view.findViewById(R.id.households_recharge_hi_de_rv);

        sharedPrefManager=new SharedPrefManager(getContext());
        myDatabase=new MyDatabase(getContext());


        getHouseholdRechargeHistoryFromDB();
     return  view;
    }
    private void getHouseholdRechargeHistoryFromDB()
    {
       /* LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        hrhdRV.setLayoutManager(llm);
        arrlist=new ArrayList<>();
        arrlist=myDatabase.getHouseholdsRechargeDetails(meterno);
        List<HRHDetailsModel> dlist2=new ArrayList<>();
        dlist2.add(new HRHDetailsModel("Household Name","Houshold Number","Date andTtime.ad","Amount"));
        dlist2.addAll(arrlist);
        rhdadapter=new RechargeHisDetailsAdapter(dlist2);
        hrhdRV.setAdapter(rhdadapter); */

        getHouseholdsRechargeHistory();
    }
    private void getHouseholdsRechargeHistory() {
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
       hrhdRV.setLayoutManager(llm);
        String token = sharedPrefManager.getAccessToken();
        if (token == null) {
            Log.e("callHouhold", "Token is null!");
            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }
        String tokenstr = "Bearer " + token;



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        String url="RechargeApi/"+meterno;

        Call<List< HRHDetailsModel>> call=requestApi.getHouseholdsRechargeHistory(url,tokenstr);

        call.enqueue(new Callback<List< HRHDetailsModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<List< HRHDetailsModel>> call, @NonNull Response<List< HRHDetailsModel>> response) {
                if (response.isSuccessful()) {
                    Log.v("mhaha"," meter have no error");
                    List<HRHDetailsModel> responselist=response.body();



                    List<HRHDetailsModel> dlist2=new ArrayList<>();
                    String cum_eb_kwh ="cum_eb_kwh";
                    String balance_amount="balance amount";
                    double dcum_eb_kwh = StringToDoubleStorage.stringToDouble(cum_eb_kwh);
                    double dbalance_amount = StringToDoubleStorage.stringToDouble(balance_amount);
                    dlist2.add(new HRHDetailsModel("Household Name","Houshold Number","Date andTtime.ad","Amount"));
                    dlist2.addAll(responselist);
                    rhdadapter=new RechargeHisDetailsAdapter(dlist2);
                    hrhdRV.setAdapter(rhdadapter);

                 /*   dlist2.add(new HouseholdsDetailsModel("Household Number",dcum_eb_kwh,dbalance_amount,"Date andTtime"));
                    dlist2.addAll(responselist);
                    householdsDetailsAdapter=new HouseholdsDetailsAdapter(dlist2);
                    hrhdRV.setAdapter(householdsDetailsAdapter);
                    if(myDatabase.getHouseholdRechargeDetailsCount(meterno)==0)
                    {

                        for(HRHDetailsModel d:responselist)
                        {
                            myDatabase.addHouseholdRechargeDetails(d.getHouse(),d.getDevid(),d.getDate(),d.getAmount());
                        }
                    }
                    else{
                        List<HRHDetailsModel> beforlist=myDatabase.getHouseholdsRechargeDetails(meterno);
                        int beforlistSize=beforlist.size();

                        String  beforTimeString=beforlist.get(beforlistSize-1).getDate();
                        String beforTimeArray[]=beforTimeString.split("[-,T,:,.]");

                        LocalDateTime beforDate= of(Integer.parseInt(beforTimeArray[0]),
                                Integer.parseInt(beforTimeArray[1]),
                                Integer.parseInt(beforTimeArray[2]),
                                Integer.parseInt(beforTimeArray[3]),
                                Integer.parseInt(beforTimeArray[4]),
                                Integer.parseInt(beforTimeArray[5]));

                        for(HRHDetailsModel d:responselist)
                        {
                            String afterTimeString =d.getDate();
                            String afterTimeArray[]=afterTimeString.split("[-,T,:,.]");
                            LocalDateTime afterDate= of(Integer.parseInt(afterTimeArray[0]),
                                    Integer.parseInt(afterTimeArray[1]),
                                    Integer.parseInt(afterTimeArray[2]),
                                    Integer.parseInt(afterTimeArray[3]),
                                    Integer.parseInt(afterTimeArray[4]),
                                    Integer.parseInt(afterTimeArray[5]));

                            if(afterDate.isAfter(beforDate))
                            {
                                myDatabase.addHouseholdRechargeDetails(d.getHouse(),d.getDevid(),d.getDate(),d.getAmount());
                            }

                        }

                    }
                   */

                }
                else
                {
                    Toast.makeText(getContext()," householdRechargeDetail nahi mili ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<HRHDetailsModel>> call, Throwable t) {
                Toast.makeText(getContext()," please check internet connection ",Toast.LENGTH_SHORT).show();
                Log.v("merr",t.toString());
            }
        });

    }




}