package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ozi.kmobil.Model.GetInsertReview;
import com.example.ozi.kmobil.Model.GetLogin;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertReviewActivity extends AppCompatActivity {
    TextView tvNamaKatalog;
    Spinner spinnerRating;
    EditText edtDeskripsi;
    String id_mobil, id_user;
    Button btTambah, btBack;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_review);

        tvNamaKatalog = (TextView) findViewById(R.id.tvNamaKatalog);
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);
        edtDeskripsi = (EditText) findViewById(R.id.edtDeskripsi);
        btTambah = (Button) findViewById(R.id.btTambah);
        btBack = (Button) findViewById(R.id.btBack);

        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        Intent mIntent = getIntent();
        tvNamaKatalog.setText("Anda akan mereview "+mIntent.getStringExtra("nama_katalog"));
        id_mobil = mIntent.getStringExtra("id_mobil");
        id_user = preferences.getString("id_user","");

        btTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                RequestBody reqIdMobil = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (id_mobil));
                RequestBody reqIdUser = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (id_user));
                RequestBody reqRating = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (spinnerRating.getSelectedItem().toString()));
                RequestBody reqDeskripsi = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtDeskripsi.getText().toString()));


                Call<GetInsertReview> mInsertCall = mApiInterface.postMyReview(reqIdMobil, reqIdUser, reqRating, reqDeskripsi);
                mInsertCall.enqueue(new Callback<GetInsertReview>() {
                    @Override
                    public void onResponse(Call<GetInsertReview> call, Response<GetInsertReview> response) {
                        Log.d("Login", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(InsertReviewActivity.this, "Gagal mereview!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InsertReviewActivity.this, "Sukses mereview!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetInsertReview> call, Throwable t) {
                        Log.d("Login", t.getMessage());
                    }
                });

                Intent myReviewIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(myReviewIntent);

            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
