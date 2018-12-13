package com.example.ozi.kmobil.Rest;

import com.example.ozi.kmobil.Model.GetDelete;
import com.example.ozi.kmobil.Model.GetEditUser;
import com.example.ozi.kmobil.Model.GetEditReview;
import com.example.ozi.kmobil.Model.GetEditUser;
import com.example.ozi.kmobil.Model.GetInsertReview;
import com.example.ozi.kmobil.Model.GetMobil;
import com.example.ozi.kmobil.Model.GetLogin;
import com.example.ozi.kmobil.Model.GetMobil;
import com.example.ozi.kmobil.Model.GetProfil;
import com.example.ozi.kmobil.Model.GetProfil;
import com.example.ozi.kmobil.Model.GetRegistrasi;
import com.example.ozi.kmobil.Model.GetReviewMobil;
import com.example.ozi.kmobil.Model.GetReviewUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("user/login")
    Call<GetLogin> postLogin(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    @Multipart
    @POST("user/registrasi")
    Call<GetRegistrasi> postRegister(
            @Part("nama_user") RequestBody nama_user,
            @Part("alamat_user") RequestBody alamat_user,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    @Multipart
    @POST("user/myprofil")
    Call<GetProfil> getProfil(
            @Part("id_user") RequestBody id_user
    );

    @Multipart
    @POST("user/myedit")
    Call<GetEditUser> postEditUser(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("nama_user") RequestBody nama_user,
            @Part("alamat_user") RequestBody alamat_user
    );


    @GET("mobil/all")
    Call<GetMobil> getMobil();

    @Multipart
    @POST("review/bymobil")
    Call<GetReviewMobil> getMobil(
            @Part("id_mobil") RequestBody id_mobil
    );

    @Multipart
    @POST("review/editmy")
    Call<GetEditReview> postEditReview(
            @Part("id_review") RequestBody id_review,
            @Part("rating") RequestBody rating,
            @Part("deskripsi") RequestBody deskripsi
    );

    @Multipart
    @POST("review/byuser")
    Call<GetReviewUser> getUser(
            @Part("id_user") RequestBody id_user
    );

    @Multipart
    @POST("review/deletemy")
    Call<GetDelete> postDeleteReview(
            @Part("id_review") RequestBody id_review
    );

    @Multipart
    @POST("review/addmy")
    Call<GetInsertReview> postMyReview(
            @Part("id_review") RequestBody id_review,
            @Part("id_mobil") RequestBody id_mobil,
            @Part("id_user") RequestBody id_user,
            @Part("rating") RequestBody rating,
            @Part("deskripsi") RequestBody deskripsi
    );

}