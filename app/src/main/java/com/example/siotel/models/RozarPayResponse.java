//package com.example.siotel.models;
//
//public class RozarPayResponse {
//   RzResponse responseObject;
//
//    public RozarPayResponse() {
//    }
//
//    public RozarPayResponse(RzResponse response) {
//        this.responseObject = response;
//    }
//
//    public RzResponse getResponseObject() {
//        return responseObject;
//    }
//
//    public void setResponseObject(RzResponse responseObject) {
//        this.responseObject = responseObject;
//    }
//}


package com.example.siotel.models;

public class RozarPayResponse {
    private String id;
    private String entity;
    private int amount;
    private String currency;
    private String status;
    private String order_id;
    private String invoice_id;
    private boolean international;
    private String method;
    private int amount_refunded;
    private String refund_status;
    private boolean captured;
    private String description;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String email;
    private String contact;
    private Notes notes;
    private String fee;
    private String tax;
    private String error_code;
    private String error_description;
    private String error_source;
    private String error_step;
    private String error_reason;
    private AcquirerData acquirer_data;
    private long created_at;
    private UpiData upi;

    // Add new fields here
    private String razorpay_signature;
    private String devEui;

    // Constructor
    public RozarPayResponse() {
    }


    // Add new getters and setters here
    public String getRazorpay_signature() {
        return razorpay_signature;
    }

    public void setRazorpay_signature(String razorpay_signature) {
        this.razorpay_signature = razorpay_signature;
    }

    public String getDevEui() {
        return devEui;
    }



    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getInvoice_id() { return invoice_id; }
    public void setInvoice_id(String invoice_id) { this.invoice_id = invoice_id; }

    public boolean isInternational() { return international; }
    public void setInternational(boolean international) { this.international = international; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public int getAmount_refunded() { return amount_refunded; }
    public void setAmount_refunded(int amount_refunded) { this.amount_refunded = amount_refunded; }

    public String getRefund_status() { return refund_status; }
    public void setRefund_status(String refund_status) { this.refund_status = refund_status; }

    public boolean isCaptured() { return captured; }
    public void setCaptured(boolean captured) { this.captured = captured; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCard_id() { return card_id; }
    public void setCard_id(String card_id) { this.card_id = card_id; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public String getWallet() { return wallet; }
    public void setWallet(String wallet) { this.wallet = wallet; }

    public String getVpa() { return vpa; }
    public void setVpa(String vpa) { this.vpa = vpa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public Notes getNotes() { return notes; }
    public void setNotes(Notes notes) { this.notes = notes; }

    public String getFee() { return fee; }
    public void setFee(String fee) { this.fee = fee; }

    public String getTax() { return tax; }
    public void setTax(String tax) { this.tax = tax; }

    public String getError_code() { return error_code; }
    public void setError_code(String error_code) { this.error_code = error_code; }

    public String getError_description() { return error_description; }
    public void setError_description(String error_description) { this.error_description = error_description; }

    public String getError_source() { return error_source; }
    public void setError_source(String error_source) { this.error_source = error_source; }

    public String getError_step() { return error_step; }
    public void setError_step(String error_step) { this.error_step = error_step; }

    public String getError_reason() { return error_reason; }
    public void setError_reason(String error_reason) { this.error_reason = error_reason; }

    public AcquirerData getAcquirer_data() { return acquirer_data; }
    public void setAcquirer_data(AcquirerData acquirer_data) { this.acquirer_data = acquirer_data; }

    public long getCreated_at() { return created_at; }
    public void setCreated_at(long created_at) { this.created_at = created_at; }

    public UpiData getUpi() { return upi; }
    public void setUpi(UpiData upi) { this.upi = upi; }


    // Inner classes for nested objects
    public static class Notes {
        private String shipping_address;

        public String getShipping_address() { return shipping_address; }
        public void setShipping_address(String shipping_address) { this.shipping_address = shipping_address; }
    }

    public static class AcquirerData {
        private String rrn;

        public String getRrn() { return rrn; }
        public void setRrn(String rrn) { this.rrn = rrn; }
    }

    public static class UpiData {
        private String payer_account_type;
        private String vpa;

        public String getPayer_account_type() { return payer_account_type; }
        public void setPayer_account_type(String payer_account_type) { this.payer_account_type = payer_account_type; }

        public String getVpa() { return vpa; }
        public void setVpa(String vpa) { this.vpa = vpa; }
    }
}