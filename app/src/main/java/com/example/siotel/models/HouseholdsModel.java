package com.example.siotel.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class HouseholdsModel {
    private String householdname;
    private String metersno;
    private String date;

    public HouseholdsModel() {
    }

    public HouseholdsModel(String householdname, String metersno, String date) {
        this.householdname = householdname;
        this.metersno = metersno;
        this.date = date;
    }

    public String getHouseholdname() { return householdname; }
    public void setHouseholdname(String value) { this.householdname = value; }

    public String getMetersno() { return metersno; }
    public void setMetersno(String value) { this.metersno = value; }

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }
}
