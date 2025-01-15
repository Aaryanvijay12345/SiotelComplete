package com.example.siotel.models;

public class RzStatusResponse {
    String detail;

    public RzStatusResponse() {
    }

    public RzStatusResponse(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String response) {
        this.detail = response;
    }
}
