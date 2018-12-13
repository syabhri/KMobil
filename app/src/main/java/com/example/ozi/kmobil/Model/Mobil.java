package com.example.ozi.kmobil.Model;

import com.google.gson.annotations.SerializedName;

public class Mobil {

    @SerializedName("id_mobil")
    private String id_mobil;
    @SerializedName("merek_mobil")
    private String merek_mobil;
    @SerializedName("nama_mobil")
    private String nama_mobil;
    @SerializedName("tahun_mobil")
    private String tahun_mobil;

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getMerek_mobil() {
        return merek_mobil;
    }

    public void setMerek_mobil(String merek_mobil) {
        this.merek_mobil = merek_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getTahun_mobil() {
        return tahun_mobil;
    }

    public void setTahun_mobil(String tahun_mobil) {
        this.tahun_mobil = tahun_mobil;
    }


}
