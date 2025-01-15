package com.example.siotel.models;

public class PayModel {
     String email;
     String amount;
     String phone;
     String devEui;

    public PayModel() {
    }

    public PayModel(String email, String amount, String phone, String devEui) {
        this.email = email;
        this.amount = amount;
        this.phone = phone;
        this.devEui = devEui;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevEui() {
        return devEui;
    }

    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }
}
