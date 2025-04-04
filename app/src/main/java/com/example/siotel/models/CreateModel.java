package com.example.siotel.models;//package com.example.siotel.models;
//
//import com.google.gson.annotations.SerializedName;
//
//public class CreateModel {
//    @SerializedName("sno")
//    private String sno;
//    @SerializedName("hname")
//    private String hname;
//    @SerializedName("uname")
//    private String uname;
//    @SerializedName("cemail")
//    private String cemail;
//    @SerializedName("caddress")
//    private String caddress;
//    @SerializedName("open_kwheb")
//    private double openKwheb;
//    @SerializedName("close_kwheb")
//    private double closeKwheb;
//    @SerializedName("con_kwheb")
//    private double conKwheb;
//    @SerializedName("open_kwhdg")
//    private double openKwhdg;
//    @SerializedName("close_kwhdg")
//    private double closeKwhdg;
//    @SerializedName("con_kwhdg")
//    private double conKwhdg;
//    @SerializedName("ebt")
//    private double ebt;
//    @SerializedName("dgt")
//    private double dgt;
//    @SerializedName("mc")
//    private double mc;
//    @SerializedName("startdate")
//    private String startDate;
//    @SerializedName("enddate")
//    private String endDate;
//    @SerializedName("open_amount")
//    private double openAmount;
//    @SerializedName("close_amount")
//    private double closeAmount;
//    @SerializedName("actday")
//    private int actDay;
//    @SerializedName("Netamount")
//    private double netAmount;
//    @SerializedName("sitename")
//    private String siteName;
//    @SerializedName("current_date")
//    private String currentDate;
//    @SerializedName("invoice_due")
//    private String invoiceDue;
//
//    // Getters
//    public String getSno() { return sno; }
//    public String getHname() { return hname; }
//    public String getUname() { return uname; }
//    public String getCemail() { return cemail; }
//    public String getCaddress() { return caddress; }
//    public double getOpenKwheb() { return openKwheb; }
//    public double getCloseKwheb() { return closeKwheb; }
//    public double getConKwheb() { return conKwheb; }
//    public double getOpenKwhdg() { return openKwhdg; }
//    public double getCloseKwhdg() { return closeKwhdg; }
//    public double getConKwhdg() { return conKwhdg; }
//    public double getEbt() { return ebt; }
//    public double getDgt() { return dgt; }
//    public double getMc() { return mc; }
//    public String getStartDate() { return startDate; }
//    public String getEndDate() { return endDate; }
//    public double getOpenAmount() { return openAmount; }
//    public double getCloseAmount() { return closeAmount; }
//    public int getActDay() { return actDay; }
//    public double getNetAmount() { return netAmount; }
//    public String getSiteName() { return siteName; }
//    public String getCurrentDate() { return currentDate; }
//    public String getInvoiceDue() { return invoiceDue; }
//}

//package com.example.siotel.models;
//
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
//public class CreateModel {
//    private String sno;
//    private String hname;
//    private String uname;
//    private String cemail;
//    private String caddress;
//
//    @SerializedName("open_kwheb")
//    private double Eb_kwh_open;
//    @SerializedName("close_kwheb")
//    private double Eb_kwh_close;
//    @SerializedName("con_kwheb")
//    private double con_eb_kwh;
//    @SerializedName("open_kwhdg")
//    private double dg_kwh_open;
//    @SerializedName("close_kwhdg")
//    private double dg_kwh_close;
//    @SerializedName("con_kwhdg")
//    private double con_dg_kwh;
//
//    @SerializedName("ebt")
//    private double eb_tf;
//    @SerializedName("dgt")
//    private double dg_tf;
//    @SerializedName("mc")
//    private double dc_tf;
//
//    @SerializedName("open_amount")
//    private double amount_open;
//    @SerializedName("close_amount")
//    private double amount_close;
//    @SerializedName("actday")
//    private int activate_days;
//    @SerializedName("Netamount")
//    private double net_amount;
//
//    private String sitename;
//    private String startdate;
//    private String enddate;
//
//    @SerializedName("current_date")
//    private String currentDate;
//    @SerializedName("invoice_due")
//    private String invoiceDue;
//
//    // Getters
//    public String getSno() { return sno; }
//    public String getHname() { return hname; }
//    public String getUname() { return uname; }
//    public String getCemail() { return cemail; }
//    public String getCaddress() { return caddress; }
//    public double getEb_kwh_open() { return Eb_kwh_open; }
//    public double getEb_kwh_close() { return Eb_kwh_close; }
//    public double getCon_eb_kwh() { return con_eb_kwh; }
//    public double getDg_kwh_open() { return dg_kwh_open; }
//    public double getDg_kwh_close() { return dg_kwh_close; }
//    public double getCon_dg_kwh() { return con_dg_kwh; }
//    public double getEb_tf() { return eb_tf; }
//    public double getDg_tf() { return dg_tf; }
//    public double getDc_tf() { return dc_tf; }
//    public double getAmount_open() { return amount_open; }
//    public double getAmount_close() { return amount_close; }
//    public int getActivate_days() { return activate_days; }
//    public double getNet_amount() { return net_amount; }
//    public String getSitename() { return sitename; }
//    public String getStartdate() { return startdate; }
//    public String getEnddate() { return enddate; }
//    public String getCurrentDate() { return currentDate; }
//    public String getInvoiceDue() { return invoiceDue; }
//}


public class CreateModel {
    private String sno;
    private String hname;
    private String uname;
    private String cemail;
    private String caddress;
    private double open_kwheb;
    private double close_kwheb;
    private double con_kwheb;
    private double open_kwhdg;
    private double close_kwhdg;
    private double con_kwhdg;
    private double ebt;
    private double dgt;
    private double mc;
    private String startdate;
    private String enddate;
    private double open_amount;
    private double close_amount;
    private int actday;
    private double Netamount;
    private String sitename;
    private String current_date;
    private String invoice_due;

    public CreateModel(String sno, String hname, String uname, String cemail, String caddress,
                       double open_kwheb, double close_kwheb, double con_kwheb, double open_kwhdg,
                       double close_kwhdg, double con_kwhdg, double ebt, double dgt, double mc,
                       String startdate, String enddate, double open_amount, double close_amount,
                       int actday, double Netamount, String sitename, String current_date, String invoice_due) {
        this.sno = sno;
        this.hname = hname;
        this.uname = uname;
        this.cemail = cemail;
        this.caddress = caddress;
        this.open_kwheb = open_kwheb;
        this.close_kwheb = close_kwheb;
        this.con_kwheb = con_kwheb;
        this.open_kwhdg = open_kwhdg;
        this.close_kwhdg = close_kwhdg;
        this.con_kwhdg = con_kwhdg;
        this.ebt = ebt;
        this.dgt = dgt;
        this.mc = mc;
        this.startdate = startdate;
        this.enddate = enddate;
        this.open_amount = open_amount;
        this.close_amount = close_amount;
        this.actday = actday;
        this.Netamount = Netamount;
        this.sitename = sitename;
        this.current_date = current_date;
        this.invoice_due = invoice_due;
    }

    // Getters (needed for binding data in the adapter)
    public String getSno() { return sno; }
    public String getHname() { return hname; }
    public String getUname() { return uname; }
    public String getCemail() { return cemail; }
    public String getCaddress() { return caddress; }
    public double getOpenKwheb() { return open_kwheb; }
    public double getCloseKwheb() { return close_kwheb; }
    public double getConKwheb() { return con_kwheb; }
    public double getOpenKwhdg() { return open_kwhdg; }
    public double getCloseKwhdg() { return close_kwhdg; }
    public double getConKwhdg() { return con_kwhdg; }
    public double getEbt() { return ebt; }
    public double getDgt() { return dgt; }
    public double getMc() { return mc; }
    public String getStartdate() { return startdate; }
    public String getEnddate() { return enddate; }
    public double getOpenAmount() { return open_amount; }
    public double getCloseAmount() { return close_amount; }
    public int getActday() { return actday; }
    public double getNetamount() { return Netamount; }
    public String getSitename() { return sitename; }
    public String getCurrentDate() { return current_date; }
    public String getInvoiceDue() { return invoice_due; }
}