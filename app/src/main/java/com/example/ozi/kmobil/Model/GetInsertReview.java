package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class GetInsertReview {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}