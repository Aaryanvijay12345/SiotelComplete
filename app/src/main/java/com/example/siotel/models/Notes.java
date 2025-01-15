package com.example.siotel.models;

public class Notes {

        private String shippingAddress;

        public Notes() {
        }

        public Notes(String shippingAddress) {
                this.shippingAddress = shippingAddress;
        }

        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String value) { this.shippingAddress = value; }
}
