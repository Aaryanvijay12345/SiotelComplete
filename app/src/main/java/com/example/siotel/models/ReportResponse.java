package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName("usage_entries")
    private List<UsageEntry> usageEntries;

    private double ebKwhOpen;        // Opening electricity board (EB) kWh reading
    private double ebKwhClose;       // Closing electricity board (EB) kWh reading
    private double dgKwhOpen;        // Opening diesel generator (DG) kWh reading
    private double dgKwhClose;       // Closing diesel generator (DG) kWh reading
    private double ebTf;             // EB tariff rate per kWh
    private double dgTf;             // DG tariff rate per kWh
    private double dcTf;             // Demand charge tariff
    private double amountOpen;       // Opening amount (e.g., previous balance)
    private double amountClose;      // Closing amount (e.g., current balance)
    private double totalRecharge;    // Total recharge amount
    private String actualStartDate;  // Actual start date of the billing period
    private String actualEndDate;    // Actual end date of the billing period
    private double totalCamAmount;   // Total CAM (Common Area Maintenance) amount
    private double totalExceptCamAmount; // Total amount excluding CAM




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

    public List<UsageEntry> getUsageEntries() { return usageEntries; }



    public double getEbKwhOpen() { return ebKwhOpen; }
    public double getEbKwhClose() { return ebKwhClose; }
    public double getDgKwhOpen() { return dgKwhOpen; }
    public double getDgKwhClose() { return dgKwhClose; }
    public double getEbTf() { return ebTf; }
    public double getDgTf() { return dgTf; }
    public double getDcTf() { return dcTf; }
    public double getAmountOpen() { return amountOpen; }
    public double getAmountClose() { return amountClose; }
    public double getTotalRecharge() { return totalRecharge; }
    public String getActualStartDate() { return actualStartDate; }
    public String getActualEndDate() { return actualEndDate; }
    public double getTotalCamAmount() { return totalCamAmount; }
    public double getTotalExceptCamAmount() { return totalExceptCamAmount; }


}