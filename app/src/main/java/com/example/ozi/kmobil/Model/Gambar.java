package com.example.ozi.kmobil.Model;


import com.google.gson.annotations.SerializedName;

public class Gambar {
    @SerializedName("id_gambar")
    private String id_gambar;
    @SerializedName("id_mobil")
    private String id_mobil;
    @SerializedName("upload_path")
    private String upload_path;

    public Gambar(String id_gambar, String id_mobil, String upload_path) {
        this.id_gambar = id_gambar;
        this.id_mobil = id_mobil;
        this.upload_path = upload_path;
    }

    public String getId_gambar() {
        return id_gambar;
    }

    public void setId_gambar(String id_gambar) {
        this.id_gambar = id_gambar;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

}