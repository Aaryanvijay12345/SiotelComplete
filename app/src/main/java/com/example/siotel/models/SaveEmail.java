package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

public class SaveEmail {

    @SerializedName("email")  // Ensure this matches the API key
    String email;

//    public SaveEmail() {
//    }

    public SaveEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
