package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsumerMeterInformationModel {

    private String MeterSN;
    private double cum_eb_kwh;
    private double balance_amount;
    private String date;

    @SerializedName("usage_entries")
    private List<UsageEntry> usageEntries;



    public ConsumerMeterInformationModel() {
    }


    public ConsumerMeterInformationModel(String meterSN, double cum_eb_kwh, double balance_amount, String date) {
        MeterSN = meterSN;
        this.cum_eb_kwh = cum_eb_kwh;
        this.balance_amount = balance_amount;
        this.date = date;

    }


    public String getMeterSN() {
        return MeterSN;
    }

    public void setMeterSN(String meterSN) {
        MeterSN = meterSN;
    }

    public double getCum_eb_kwh() {
        return cum_eb_kwh;
    }

    public void setCum_eb_kwh(double cum_eb_kwh) {
        this.cum_eb_kwh = cum_eb_kwh;
    }

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<UsageEntry> getUsageEntries() { return usageEntries; }

}
