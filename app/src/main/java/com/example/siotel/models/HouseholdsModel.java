package com.example.siotel.models;

import com.google.gson.annotations.SerializedName;

public class HouseholdsModel {
    private String householdname;
    private String metersno;
    private String date;

    @SerializedName("sitename")
    private String siteName;

    public HouseholdsModel() {
    }

    public HouseholdsModel(String householdname, String metersno, String date, String siteName) {
        this.householdname = householdname;
        this.metersno = metersno;
        this.date = date;
        this.siteName = siteName;
    }

    public HouseholdsModel(String householdname, String metersno, String date) {
        this.householdname = householdname;
        this.metersno = metersno;
        this.date = date;
        this.siteName = null;
    }

    public String getHouseholdname() { return householdname; }
    public void setHouseholdname(String value) { this.householdname = value; }

    public String getMetersno() { return metersno; }
    public void setMetersno(String value) { this.metersno = value; }

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String value) { this.siteName = value; }
}