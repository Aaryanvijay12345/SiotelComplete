package com.example.siotel.models;

public class ReportRequest {
    private String startdate;
    private String enddate;
    private String Finance_Year;

    public ReportRequest(String startdate, String enddate, String finance_Year) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.Finance_Year = finance_Year;
    }
}