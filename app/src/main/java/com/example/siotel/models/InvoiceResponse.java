package com.example.siotel.models;

public class InvoiceResponse {
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

    // Getters and setters
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public String getHname() { return hname; }
    public void setHname(String hname) { this.hname = hname; }
    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }
    public String getCemail() { return cemail; }
    public void setCemail(String cemail) { this.cemail = cemail; }
    public String getCaddress() { return caddress; }
    public void setCaddress(String caddress) { this.caddress = caddress; }
    public double getOpen_kwheb() { return open_kwheb; }
    public void setOpen_kwheb(double open_kwheb) { this.open_kwheb = open_kwheb; }
    public double getClose_kwheb() { return close_kwheb; }
    public void setClose_kwheb(double close_kwheb) { this.close_kwheb = close_kwheb; }
    public double getCon_kwheb() { return con_kwheb; }
    public void setCon_kwheb(double con_kwheb) { this.con_kwheb = con_kwheb; }
    public double getOpen_kwhdg() { return open_kwhdg; }
    public void setOpen_kwhdg(double open_kwhdg) { this.open_kwhdg = open_kwhdg; }
    public double getClose_kwhdg() { return close_kwhdg; }
    public void setClose_kwhdg(double close_kwhdg) { this.close_kwhdg = close_kwhdg; }
    public double getCon_kwhdg() { return con_kwhdg; }
    public void setCon_kwhdg(double con_kwhdg) { this.con_kwhdg = con_kwhdg; }
    public double getEbt() { return ebt; }
    public void setEbt(double ebt) { this.ebt = ebt; }
    public double getDgt() { return dgt; }
    public void setDgt(double dgt) { this.dgt = dgt; }
    public double getMc() { return mc; }
    public void setMc(double mc) { this.mc = mc; }

    public String getStartdate() { return startdate; }
    public void setStartdate(String startdate) { this.startdate = startdate; }

    public String getEnddate() { return enddate; }
    public void setEnddate(String enddate) { this.enddate = enddate; }
    public double getOpen_amount() { return open_amount; }
    public void setOpen_amount(double open_amount) { this.open_amount = open_amount; }
    public double getClose_amount() { return close_amount; }
    public void setClose_amount(double close_amount) { this.close_amount = close_amount; }
    public int getActday() { return actday; }
    public void setActday(int actday) { this.actday = actday; }
    public double getNetamount() { return Netamount; }
    public void setNetamount(double Netamount) { this.Netamount = Netamount; }
    public String getSitename() { return sitename; }
    public void setSitename(String sitename) { this.sitename = sitename; }
    public String getCurrent_date() { return current_date; }
    public void setCurrent_date(String current_date) { this.current_date = current_date; }
    public String getInvoice_due() { return invoice_due; }
    public void setInvoice_due(String invoice_due) { this.invoice_due = invoice_due; }
}