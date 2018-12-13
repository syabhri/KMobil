package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class GetRegistrasi {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private User result;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
