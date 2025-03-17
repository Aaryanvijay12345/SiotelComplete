package com.example.siotel.models;

public class ReportResponse {
    private String sno;
    private double Eb_kwh_open;
    private double Eb_kwh_close;
    private double con_eb_kwh;
    private double dg_kwh_open;
    private double dg_kwh_close;
    private double con_dg_kwh;
    private double eb_tf;
    private double dg_tf;
    private double dc_tf;
    private double amount_open;
    private double amount_close;
    private int activate_days;
    private double net_amount;
    private double total_Recharge;
    private String actual_start_date;
    private String actual_end_date;
    private double total_cam_amount;
    private double total_except_cam_amount;
    private double total_amount;

    // Getters
    public String getSno() { return sno; }
    public double getEb_kwh_open() { return Eb_kwh_open; }
    public double getEb_kwh_close() { return Eb_kwh_close; }
    public double getCon_eb_kwh() { return con_eb_kwh; }
    public double getDg_kwh_open() { return dg_kwh_open; }
    public double getDg_kwh_close() { return dg_kwh_close; }
    public double getCon_dg_kwh() { return con_dg_kwh; }
    public double getEb_tf() { return eb_tf; }
    public double getDg_tf() { return dg_tf; }
    public double getDc_tf() { return dc_tf; }
    public double getAmount_open() { return amount_open; }
    public double getAmount_close() { return amount_close; }
    public int getActivate_days() { return activate_days; }
    public double getNet_amount() { return net_amount; }
    public double getTotal_Recharge() { return total_Recharge; }
    public String getActual_start_date() { return actual_start_date; }
    public String getActual_end_date() { return actual_end_date; }
    public double getTotal_cam_amount() { return total_cam_amount; }
    public double getTotal_except_cam_amount() { return total_except_cam_amount; }
    public double getTotal_amount() { return total_amount; }
}