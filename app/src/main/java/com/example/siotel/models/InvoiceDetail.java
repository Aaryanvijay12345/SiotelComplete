package com.example.siotel.models;

public class InvoiceDetail {
    private int id;
    private String customer;
    private String customer_email;
    private String billing_address;
    private String date;
    private String due_date;
    private String message;
    private boolean status;
    private String email;
    private String adminemail;
    private String sno;
    private String open_kwheb;
    private String close_kwheb;
    private String con_kwheb;
    private String open_kwhdg;
    private String close_kwhdg;
    private String con_kwhdg;
    private String dgt;
    private String mc;
    private String actday;
    private String total_amount;
    private String ebt;
    private String sitename;
    private String startdate;
    private String enddate;
    private String recharge_amount;
    private String time;
    private String payment_id;

    // Getters and Setters for all fields
    public int getId() { return id; }
    public String getCustomer() { return customer; }
    public String getCustomerEmail() { return customer_email; }
    public String getBillingAddress() { return billing_address; }
    public String getDate() { return date; }
    public String getDueDate() { return due_date; }
    public String getMessage() { return message; }
    public boolean isStatus() { return status; }
    public String getEmail() { return email; }
    public String getAdminEmail() { return adminemail; }
    public String getSno() { return sno; }
    public String getOpenKwheb() { return open_kwheb; }
    public String getCloseKwheb() { return close_kwheb; }
    public String getConKwheb() { return con_kwheb; }
    public String getOpenKwhdg() { return open_kwhdg; }
    public String getCloseKwhdg() { return close_kwhdg; }
    public String getConKwhdg() { return con_kwhdg; }
    public String getDgt() { return dgt; }
    public String getMc() { return mc; }
    public String getActday() { return actday; }
    public String getTotalAmount() { return total_amount; }
    public String getEbt() { return ebt; }
    public String getSitename() { return sitename; }
    public String getStartdate() { return startdate; }
    public String getEnddate() { return enddate; }
    public String getRechargeAmount() { return recharge_amount; }
    public String getTime() { return time; }
    public String getPaymentId() { return payment_id; }
}
