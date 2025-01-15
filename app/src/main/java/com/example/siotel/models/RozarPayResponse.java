package com.example.siotel.models;

public class RozarPayResponse {
   RzResponse responseObject;

    public RozarPayResponse() {
    }

    public RozarPayResponse(RzResponse response) {
        this.responseObject = response;
    }

    public RzResponse getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(RzResponse responseObject) {
        this.responseObject = responseObject;
    }
}
