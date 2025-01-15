package com.example.siotel.models;

public class RzPayStatus {

    String email;
    String razorpay_order_id;
    String razorpay_payment_id;
    String devEui;
    String razorpay_signature;

    public RzPayStatus() {
    }

    public RzPayStatus(String email, String razorpay_order_id, String razorpay_payment_id, String devEui, String razorpay_signature) {
        this.email = email;
        this.razorpay_order_id = razorpay_order_id;
        this.razorpay_payment_id = razorpay_payment_id;
        this.devEui = devEui;
        this.razorpay_signature = razorpay_signature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRazorpay_order_id() {
        return razorpay_order_id;
    }

    public void setRazorpay_order_id(String razorpay_order_id) {
        this.razorpay_order_id = razorpay_order_id;
    }

    public String getRazorpay_payment_id() {
        return razorpay_payment_id;
    }

    public void setRazorpay_payment_id(String razorpay_payment_id) {
        this.razorpay_payment_id = razorpay_payment_id;
    }

    public String getDevEui() {
        return devEui;
    }

    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }

    public String getRazorpay_signature() {
        return razorpay_signature;
    }

    public void setRazorpay_signature(String razorpay_signature) {
        this.razorpay_signature = razorpay_signature;
    }
}
