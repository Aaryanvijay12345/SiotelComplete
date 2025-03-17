package com.example.siotel.models;

public class RechargeHistoryRequest {
    private String startdate;
    private String enddate;

    public RechargeHistoryRequest(String startdate, String enddate) {
        this.startdate = startdate;
        this.enddate = enddate;
    }
}