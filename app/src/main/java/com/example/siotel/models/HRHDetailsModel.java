package com.example.siotel.models;

public class HRHDetailsModel {
       String house;
            String devid;
            String date;
            String amount;

    public HRHDetailsModel() {
    }

    public HRHDetailsModel(String house, String devid, String date, String amount) {
        this.house = house;
        this.devid = devid;
        this.date = date;
        this.amount = amount;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
