package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetReviewMobil {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Review> result = new ArrayList<>();
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Review> getResult() {
        return result;
    }

    public void setResult(List<Review> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
