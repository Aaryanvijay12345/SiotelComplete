package com.example.siotel.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.siotel.activity.MainActivity;
import com.example.siotel.api.PoatRegisterApi;
import com.example.siotel.R;
import com.example.siotel.activity.RegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

   EditText firstname,lastname,email,password,repawword,username;
   Button register;
   LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_register, container, false);


           FindViewById(v);
           OnClick();


        return v;
    }
    private void OnClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRetrofit();
               // Toast.makeText(getActivity()," clik huaa h",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void FindViewById(View v){
        firstname=v.findViewById(R.id.firstname);
        lastname=v.findViewById(R.id.lastname);
        email=v.findViewById(R.id.email);
        password=v.findViewById(R.id.password);
        repawword=v.findViewById(R.id.repassword);
        register=v.findViewById(R.id.register);
        username=v.findViewById(R.id.username);
        layout=v.findViewById(R.id.innerCon);
    }
    private void RegisterRetrofit()
    {
        String firstnameString=firstname.getText().toString();
        String lastnameString=lastname.getText().toString();
        String emailstring=email.getText().toString();
        String passwordString=password.getText().toString();
        String repasswordString =repawword.getText().toString();
        String user=username.getText().toString();

        Retrofit retrofit2=new Retrofit.Builder()
                .baseUrl("http://192.168.0.166:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PoatRegisterApi poatRegisterApi=retrofit2.create(PoatRegisterApi.class);


        Call<RegisterModel> call;
        call = poatRegisterApi.RegistertoServer(user,repasswordString,firstnameString,lastnameString,emailstring);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {


                Log.v("Su",response.toString());

                RegisterModel reponseFromApi=response.body();
                if(response.isSuccessful())
                {
                    Toast.makeText(getActivity()," ho gya register",Toast.LENGTH_SHORT).show();
                    Log.v("Suc",response.toString());
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);



                }

            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //  textView.setText("Error found is : " + t.getMessage());
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
                Log.v("Tag2",t.toString());
            }
        });
    }
}