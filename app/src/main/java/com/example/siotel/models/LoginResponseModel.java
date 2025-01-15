package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("Access Token")
    String response;

    public LoginResponseModel() {
    }

    public LoginResponseModel(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
