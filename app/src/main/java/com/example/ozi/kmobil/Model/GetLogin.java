package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class GetLogin {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private String result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
