package com.example.siotel.connectapi;

import static java.time.LocalDateTime.of;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.siotel.SharedPrefManager;
import com.example.siotel.activity.MainActivity;
import com.example.siotel.adapters.HouseholdsDetailsAdapter;
import com.example.siotel.adapters.RechargeHisDetailsAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HRHDetailsModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.HouseholdDatabase;
import com.example.siotel.sqlitedatabase.MyDatabase;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllApiConnect {

    public void housholdApi(Context context)
    {
         MyDatabase myDatabase=new MyDatabase(context);
         SharedPrefManager sharedPrefManager=new SharedPrefManager(context);



        Token token=sharedPrefManager.getUser();
        String tokenstr="Bearer "+token.getToken();
        String email=token.getEmail();
        SaveEmail saveEmail=new SaveEmail(email);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();






        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<List<HouseholdsModel>> call = requestApi.getAllMeter(tokenstr,saveEmail);

        call.enqueue(new Callback<List<HouseholdsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
                if (response.isSuccessful()) {

                    Log.v("haha"," have no error");
                    List<HouseholdsModel> dlist = response.body();
                    if(myDatabase.getHouseholdCount()<dlist.size())
                    {
                        int old=myDatabase.getHouseholdCount();
                        int nw=dlist.size();

                        for(int i=old;i<nw;i++)
                        {
                            String date=dlist.get(i).getDate();
                            String arr[]=date.split("[T]");
                            myDatabase.addHouseHold(dlist.get(i).getHouseholdname(),dlist.get(i).getMetersno(),arr[0]);
                        }
                    }
                }
                else
                {
                    Toast.makeText(context,"  household api response not successfull  ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<HouseholdsModel>> call, Throwable t) {
                Toast.makeText(context,"  household api not connect  ",Toast.LENGTH_LONG).show();
                Log.v("err",t.toString());
            }
        });

    }
    public void householdDetailsApi(Context context,String meterno){


        MyDatabase myDatabase=new MyDatabase(context);
        SharedPrefManager sharedPrefManager=new SharedPrefManager(context);



        Token token=sharedPrefManager.getUser();
        String tokenstr="Bearer "+token.getToken();
        String email=token.getEmail();
        SaveEmail saveEmail=new SaveEmail(email);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        String url="houseListApi/"+meterno;
        Call<List<HouseholdsDetailsModel>> call = requestApi.getMetersDtl(url,tokenstr);

        call.enqueue(new Callback<List<HouseholdsDetailsModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<List<HouseholdsDetailsModel>> call, @NonNull Response<List<HouseholdsDetailsModel>> response) {
                if (response.isSuccessful()) {

                    List<HouseholdsDetailsModel> responselist = response.body();




                /*    for(HouseholdsDetailsModel d:responselist)
                    {
                        myDatabase.addHouseHoldDetails(d.getMeterSN(),d.getCum_eb_kwh(),
                                d.getBalance_amount(),d.getDate());
                    } */


                    if(myDatabase.getHouseholdDetailsCount(meterno)==0)
                    {
//                        int old=myDatabase.getHouseholdDetailsCount(householdId);
//                        int nw=responselist.size();
//
//                        for(int i=old;i<nw;i++)
//                        {
//
//                            myDatabase.addHouseHoldDetails(responselist.get(i).getMeterSN(),responselist.get(i).getCum_eb_kwh(),
//                                    responselist.get(i).getBalance_amount(),responselist.get(i).getDate());
//
//                        }
//                        householdsDetailsAdapter.notifyDataSetChanged();
                        for(HouseholdsDetailsModel d:responselist)
                        {
                            myDatabase.addHouseHoldDetails(d.getMeterSN(),String.valueOf(d.getCum_eb_kwh()),
                                    String.valueOf(d.getBalance_amount()),d.getDate());
                        }
                    }
                    else{
                        List<HouseholdsDetailsModel> beforlist=myDatabase.getHouseholdsDetails(meterno);
                        int beforlistSize=myDatabase.getHouseholdRechargeDetailsCount(meterno);

                        String  beforTimeString=beforlist.get(0).getDate();
                        String beforTimeArray[]=beforTimeString.split("[-,T,:,.]");

                        LocalDateTime beforDate= of(Integer.parseInt(beforTimeArray[0]),
                                Integer.parseInt(beforTimeArray[1]),
                                Integer.parseInt(beforTimeArray[2]),
                                Integer.parseInt(beforTimeArray[3]),
                                Integer.parseInt(beforTimeArray[4]),
                                Integer.parseInt(beforTimeArray[5]));

                        for(HouseholdsDetailsModel d:responselist)
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
                                myDatabase.addHouseHoldDetails(d.getMeterSN(),String.valueOf(d.getCum_eb_kwh()),
                                       String.valueOf( d.getBalance_amount()),d.getDate());
                            }

                        }

                    }


                }

            }
            @Override
            public void onFailure(Call<List<HouseholdsDetailsModel>> call, Throwable t) {
                Toast.makeText(context," household details api not connect ",Toast.LENGTH_LONG).show();
                Log.v("merr",t.toString());
            }
        });


    }
    public void householdRechargeDetailsApi(Context context,String meterno)
    {
        MyDatabase myDatabase=new MyDatabase(context);
        SharedPrefManager sharedPrefManager=new SharedPrefManager(context);


        Token token=sharedPrefManager.getUser();
        String tokenstr="Bearer "+token.getToken();
        String email=token.getEmail();
        SaveEmail saveEmail=new SaveEmail(email);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        String url="RechargeApi/"+meterno;

        Call<List<HRHDetailsModel>> call=requestApi.getHouseholdsRechargeHistory(url,tokenstr);

        call.enqueue(new Callback<List< HRHDetailsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List< HRHDetailsModel>> call, @NonNull Response<List< HRHDetailsModel>> response) {
                if (response.isSuccessful()) {

                    Log.v("mhaha"," meter have no error");
                    List<HRHDetailsModel> responselist = response.body();


                    if(myDatabase.getHouseholdRechargeDetailsCount(meterno)<responselist.size())
                    {
                        int old=myDatabase.getHouseholdRechargeDetailsCount(meterno);
                        int nw=responselist.size();

                        for(int i=old;i<nw;i++)
                        {
                            myDatabase.addHouseholdRechargeDetails(responselist.get(i).getHouse(),responselist.get(i).getDevid(),
                                    responselist.get(i).getDate(),responselist.get(i).getAmount());

                        }
                    }


                    /*for ( HRHDetailsModel d : responselist) {

                        myDatabase.addHouseholdRechargeDetails(d.getHouse(),d.getDevid(),
                           d.getDate(),d.getAmount());
                    } */
                }
                else
                {
                    Toast.makeText(context," householdRechargeDetail nahi mili ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<HRHDetailsModel>> call, Throwable t) {
                Log.v("merr",t.toString());
            }
        });
    }

    public void totalRecharge(Context context)
    {

        MyDatabase myDatabase=new MyDatabase(context);
        SharedPrefManager sharedPrefManager=new SharedPrefManager(context);

        Token token=sharedPrefManager.getUser();
        String tokenstr="Bearer "+token.getToken();
        String email=token.getEmail();
        SaveEmail saveEmail=new SaveEmail(email);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<Integer> call = requestApi.getTotalRecharge(tokenstr,saveEmail);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {



                if (response.isSuccessful()) {
                    int totalrcg=response.body();


                    if(myDatabase.getRechargeDB()==Integer.MIN_VALUE)
                          myDatabase.addRecharge(totalrcg);
                    if(myDatabase.getRechargeDB()!=totalrcg)
                          myDatabase.updateRecharge(totalrcg);




                }
                else {
                    Toast.makeText(context," recharge get me kush grbr ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v("err",t.toString());
                Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
