package com.example.siotel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.siotel.models.LoginModel;
import com.example.siotel.models.LoginResponseModel;
import com.example.siotel.models.SaveUser;
import com.example.siotel.models.Token;

public class SharedPrefManager {

    private static String SHARED_PRE_NAME="sharedpre";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context)
    {
        this.context=context;
        sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);

    }

   public  void saveUser(SaveUser saveUser)
    {
        editor=sharedPreferences.edit();
        editor.putString("token",saveUser.getSaveToken());
        editor.putString("email",saveUser.getSaveEmail());
        editor.putBoolean("logged",true);
        editor.apply();
    }

 public    boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }

    public Token getUser()
    {
        sharedPreferences=context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        return  new Token(sharedPreferences.getString("token",null),sharedPreferences.getString("email",null));
    }
    public String getAccessToken() {
        return sharedPreferences.getString("token", null);  // Retrieve the access token
    }

    public String getEmail() {
        return sharedPreferences.getString("email", null);  // Retrieve the email
    }
    public  void logout()
    {
        sharedPreferences=context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
