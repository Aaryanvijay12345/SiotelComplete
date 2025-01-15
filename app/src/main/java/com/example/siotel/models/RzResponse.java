package com.example.siotel.models;
// Welcome1.java



public class RzResponse {
    private String id;
    private String entity;
    private long amount;
    private long amountPaid;
    private long amountDue;
    String currency;
    private String receipt;
    private Object offerID;
    private String status;
    private long attempts;
    private Notes notes;
    private long createdAt;
    private String skey;

    public RzResponse() {
    }

    public RzResponse(String id, String entity, long amount, long amountPaid, long amountDue, String currency, String receipt, Object offerID, String status, long attempts, Notes notes, long createdAt,String skey) {
        this.id = id;
        this.entity = entity;
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.amountDue = amountDue;
        this.currency = currency;
        this.receipt = receipt;
        this.offerID = offerID;
        this.status = status;
        this.attempts = attempts;
        this.notes = notes;
        this.createdAt = createdAt;
        this.skey=skey;
    }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getEntity() { return entity; }
    public void setEntity(String value) { this.entity = value; }

    public long getAmount() { return amount; }
    public void setAmount(long value) { this.amount = value; }

    public long getAmountPaid() { return amountPaid; }
    public void setAmountPaid(long value) { this.amountPaid = value; }

    public long getAmountDue() { return amountDue; }
    public void setAmountDue(long value) { this.amountDue = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    public String getReceipt() { return receipt; }
    public void setReceipt(String value) { this.receipt = value; }

    public Object getOfferID() { return offerID; }
    public void setOfferID(Object value) { this.offerID = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public long getAttempts() { return attempts; }
    public void setAttempts(long value) { this.attempts = value; }

    public Notes getNotes() { return notes; }
    public void setNotes(Notes value) { this.notes = value; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long value) { this.createdAt = value; }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
}

// Notes.java

