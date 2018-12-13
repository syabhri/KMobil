package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("id_user")
    private String id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
