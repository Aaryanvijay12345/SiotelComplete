package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

public class TokenCl {
    @SerializedName("Access Token") // Make sure this matches the JSON key
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
