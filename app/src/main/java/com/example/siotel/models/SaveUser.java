package com.example.siotel.models;

public class SaveUser {

    public String saveToken;
    public String saveEmail;

    public SaveUser() {
    }

    public SaveUser(String saveToken, String saveEmail) {
        this.saveToken = saveToken;
        this.saveEmail = saveEmail;
    }

    public String getSaveToken() {
        return saveToken;
    }

    public void setSaveToken(String saveToken) {
        this.saveToken = saveToken;
    }

    public String getSaveEmail() {
        return saveEmail;
    }

    public void setSaveEmail(String saveEmail) {
        this.saveEmail = saveEmail;
    }
}
