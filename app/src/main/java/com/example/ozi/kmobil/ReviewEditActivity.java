package com.example.ozi.kmobil;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ozi.kmobil.Model.GetDelete;
import com.example.ozi.kmobil.Model.GetEditReview;
import com.example.ozi.kmobil.Model.GetRegistrasi;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewEditActivity extends AppCompatActivity {
    Spinner spinnerRating;
    EditText edtDeskripsi;
    Button btUpdate, btBack, btDelete;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_edit);

        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);
        edtDeskripsi = (EditText) findViewById(R.id.edtDeskripsi);
        btBack = (Button) findViewById(R.id.btBack);
        btUpdate = (Button) findViewById(R.id.btEdit);
        btDelete = (Button) findViewById(R.id.btDelete);

        final Intent mIntent = getIntent();
        spinnerRating.setSelection(Integer.parseInt(mIntent.getStringExtra("rating"))-1);
        edtDeskripsi.setText(mIntent.getStringExtra("Deskripsi"));

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                RequestBody reqIdReview = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_review")));
                RequestBody reqRating = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (spinnerRating.getSelectedItem().toString()));
                RequestBody reqDeskripsi = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtDeskripsi.getText().toString()));
                Call<GetEditReview> mEditReviewCall = mApiInterface.postEditReview(reqIdReview, reqRating, reqDeskripsi);
                mEditReviewCall.enqueue(new Callback<GetEditReview>() {
                    @Override
                    public void onResponse(Call<GetEditReview> call, Response<GetEditReview> response) {
                        Log.d("EditReview", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(ReviewEditActivity.this, "Gagal Edit", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ReviewEditActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetEditReview> call, Throwable t) {
                        Log.d("Login", t.getMessage());
                    }
                });


                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                RequestBody reqIdReviewan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_reviewan")));
                Call<GetDelete> mDeleteReviewCall = mApiInterface.postDeleteReview(reqIdReviewan);
                mDeleteReviewCall.enqueue(new Callback<GetDelete>() {
                    @Override
                    public void onResponse(Call<GetDelete> call, Response<GetDelete> response) {
                        //Log.d("DeleteReview", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(ReviewEditActivity.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ReviewEditActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDelete> call, Throwable t) {
                        //Log.d("Login", t.getMessage());
                        Toast.makeText(ReviewEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

    }
}
