package com.example.siotel.models;

public class RechargeHistoryItem {
    private String house;
    private String devid;
    private String date;
    private String amount;

    public RechargeHistoryItem(String house, String devid, String date, String amount) {
        this.house = house;
        this.devid = devid;
        this.date = date;
        this.amount = amount;
    }

    public String getHouse() {
        return house;
    }

    public String getDevid() {
        return devid;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }
}