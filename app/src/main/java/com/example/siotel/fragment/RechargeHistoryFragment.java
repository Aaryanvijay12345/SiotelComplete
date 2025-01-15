package com.example.siotel.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.adapters.HouseHoldsAdapter;
import com.example.siotel.adapters.RechargeHistoryFragHousHoldsAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.example.siotel.sqlitedatabase.HouseholdDatabase;
import com.example.siotel.sqlitedatabase.MyDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RechargeHistoryFragment extends Fragment {

    List<HouseholdsModel> arrayList;
    SharedPrefManager sharedPrefManager;
    RechargeHistoryFragHousHoldsAdapter rhfa;
    RecyclerView householdsRv;

    MyDatabase myDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recharge_history, container, false);


        findById(view);
        getHouseholdDataFromDatabase();

        return view;
    }
    private void findById(View view)
    {
        householdsRv=view.findViewById(R.id.household_rv);
        sharedPrefManager=new SharedPrefManager(getContext());
        myDatabase=new MyDatabase(getContext());
    }
    private void getHouseholdDataFromDatabase()
    {
        GridLayoutManager llm=new GridLayoutManager(getContext(),2);
        householdsRv.setLayoutManager(llm);
        arrayList=myDatabase.getHouseHolds();
        rhfa=new RechargeHistoryFragHousHoldsAdapter(getContext(),arrayList);
        householdsRv.setAdapter(rhfa);
    }
    private void gethousholdsData() {




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
                        rhfa.notifyDataSetChanged();
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