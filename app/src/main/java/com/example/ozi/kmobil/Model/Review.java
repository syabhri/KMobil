package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("id_mobil")
    private String id_mobil;
    @SerializedName("id_review")
    private String id_review;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("rating")
    private int rating;

    public Review(String deskripsi, String id_mobil, String id_review, String id_user, int rating) {
        this.deskripsi = deskripsi;
        this.id_mobil = id_mobil;
        this.id_review = id_review;
        this.id_user = id_user;
        this.rating = rating;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getId_review() {
        return id_review;
    }

    public void setId_review(String id_review) {
        this.id_review = id_review;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}

