package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

public class Invoice {
    private int id;
    private String customer;

    @SerializedName("customer_email")
    private String customerEmail;

    @SerializedName("billing_address")
    private String billingAddress;

    private String date;

    @SerializedName("due_date")
    private String dueDate;

    private boolean status;
    private String sno;

    @SerializedName("open_kwheb")
    private double openKwheb;

    @SerializedName("close_kwheb")
    private double closeKwheb;

    @SerializedName("con_kwheb")
    private double conKwheb;

    @SerializedName("total_amount")
    private double totalAmount;

    private double ebt;

    @SerializedName("site_name")
    private String sitename;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    public Invoice() {}

    // Getters
    public int getId() { return id; }
    public String getCustomer() { return customer; }
    public String getCustomerEmail() { return customerEmail; }
    public String getBillingAddress() { return billingAddress; }
    public String getDate() { return date; }
    public String getDueDate() { return dueDate; }
    public boolean isStatus() { return status; }
    public String getSno() { return sno; }
    public double getOpenKwheb() { return openKwheb; }
    public double getCloseKwheb() { return closeKwheb; }
    public double getConKwheb() { return conKwheb; }
    public double getTotalAmount() { return totalAmount; }
    public double getEbt() { return ebt; }
    public String getSitename() { return sitename; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
}
