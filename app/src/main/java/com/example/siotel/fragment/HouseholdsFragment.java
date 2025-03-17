package com.example.siotel.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.interfaces.RecyclerViewInterface;
import com.example.siotel.models.HhModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.HouseholdDatabase;
import com.example.siotel.sqlitedatabase.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HouseholdsFragment extends Fragment    {
    List<HouseholdsModel> arrayList;
    HouseHoldsAdapter houseHoldsAdapter;
    RecyclerView devicesRV;
    Button HouseHoldDetails,householdRecharge;
    SharedPrefManager sharedPrefManager;
    MyDatabase myDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_households, container, false);

        sharedPrefManager=new SharedPrefManager(getActivity().getApplicationContext());
        devicesRV=v.findViewById(R.id.devices_rv);
        householdRecharge=v.findViewById(R.id.household_recharge_but);
        HouseHoldDetails=v.findViewById(R.id.household_details_but);
        setHouseHoldsFromDatabase();
        // OnClick();


        return  v;
    }

    public void setHouseHoldsFromDatabase()
    {

        arrayList=new ArrayList<>();
        GridLayoutManager llm=new GridLayoutManager(getContext(),2);
        devicesRV.setLayoutManager(llm);

        myDatabase=new MyDatabase(getContext());
        arrayList=myDatabase.getHouseHolds();

        houseHoldsAdapter=new HouseHoldsAdapter(getContext(),arrayList);
        devicesRV.setAdapter(houseHoldsAdapter);

        callHouseholdApi();
    }
    private void callHouseholdApi() {



        Token token=sharedPrefManager.getUser();
        String tokenstr="Bearer "+token.getToken();
        String email=token.getEmail();
        SaveEmail saveEmail=new SaveEmail(email);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();






        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<List<HouseholdsModel>> call = requestApi.getAllMeter(tokenstr,saveEmail);

        call.enqueue(new Callback<List<HouseholdsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
                if (response.isSuccessful()) {

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
                        houseHoldsAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"  household api response not successfull  ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<HouseholdsModel>> call, Throwable t) {
                Toast.makeText(getContext()," please check internet connection ",Toast.LENGTH_SHORT).show();
                Log.v("err",t.toString());
            }
        });


    }

}