package com.example.siotel.models;

public class InvoiceRequest {
    private String startdate;
    private String enddate;
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

    private String meterSno;

    // Getters and setters
    public String getStartdate() { return startdate; }
    public void setStartdate(String startdate) { this.startdate = startdate; }
    public String getEnddate() { return enddate; }
    public void setEnddate(String enddate) { this.enddate = enddate; }
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public double getEb_kwh_open() { return Eb_kwh_open; }
    public void setEb_kwh_open(double Eb_kwh_open) { this.Eb_kwh_open = Eb_kwh_open; }
    public double getEb_kwh_close() { return Eb_kwh_close; }
    public void setEb_kwh_close(double Eb_kwh_close) { this.Eb_kwh_close = Eb_kwh_close; }
    public double getCon_eb_kwh() { return con_eb_kwh; }
    public void setCon_eb_kwh(double con_eb_kwh) { this.con_eb_kwh = con_eb_kwh; }
    public double getDg_kwh_open() { return dg_kwh_open; }
    public void setDg_kwh_open(double dg_kwh_open) { this.dg_kwh_open = dg_kwh_open; }
    public double getDg_kwh_close() { return dg_kwh_close; }
    public void setDg_kwh_close(double dg_kwh_close) { this.dg_kwh_close = dg_kwh_close; }
    public double getCon_dg_kwh() { return con_dg_kwh; }
    public void setCon_dg_kwh(double con_dg_kwh) { this.con_dg_kwh = con_dg_kwh; }
    public double getEb_tf() { return eb_tf; }
    public void setEb_tf(double eb_tf) { this.eb_tf = eb_tf; }
    public double getDg_tf() { return dg_tf; }
    public void setDg_tf(double dg_tf) { this.dg_tf = dg_tf; }
    public double getDc_tf() { return dc_tf; }
    public void setDc_tf(double dc_tf) { this.dc_tf = dc_tf; }
    public double getAmount_open() { return amount_open; }
    public void setAmount_open(double amount_open) { this.amount_open = amount_open; }
    public double getAmount_close() { return amount_close; }
    public void setAmount_close(double amount_close) { this.amount_close = amount_close; }
    public int getActivate_days() { return activate_days; }
    public void setActivate_days(int activate_days) { this.activate_days = activate_days; }
    public double getNet_amount() { return net_amount; }
    public void setNet_amount(double net_amount) { this.net_amount = net_amount; }
    public double getTotal_Recharge() { return total_Recharge; }
    public void setTotal_Recharge(double total_Recharge) { this.total_Recharge = total_Recharge; }
    public String getActual_start_date() { return actual_start_date; }
    public void setActual_start_date(String actual_start_date) { this.actual_start_date = actual_start_date; }
    public String getActual_end_date() { return actual_end_date; }
    public void setActual_end_date(String actual_end_date) { this.actual_end_date = actual_end_date; }
    public double getTotal_cam_amount() { return total_cam_amount; }
    public void setTotal_cam_amount(double total_cam_amount) { this.total_cam_amount = total_cam_amount; }
    public double getTotal_except_cam_amount() { return total_except_cam_amount; }
    public void setTotal_except_cam_amount(double total_except_cam_amount) { this.total_except_cam_amount = total_except_cam_amount; }
    public double getTotal_amount() { return total_amount; }
    public void setTotal_amount(double total_amount) { this.total_amount = total_amount; }

    public void setMeterSno(String meterSno) {
        this.meterSno = meterSno;
    }
}