package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ozi.kmobil.Adapter.ReviewMobilAdapter;
import com.example.ozi.kmobil.Model.GetMobil;
import com.example.ozi.kmobil.Model.GetReviewMobil;
import com.example.ozi.kmobil.Model.Mobil;
import com.example.ozi.kmobil.Model.Review;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobilDetailActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btBack, btTambah;

    TextView tvIdKatalog, tvNamaKatalog;
    ImageView ivPhotoMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil_detail);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btBack = (Button) findViewById(R.id.btBack);
        btTambah = (Button) findViewById(R.id.btTambah);

        tvIdKatalog = (TextView) findViewById(R.id.tvIdKatalog);
        tvNamaKatalog = (TextView) findViewById(R.id.tvNamaKatalog);
        ivPhotoMobil = (ImageView) findViewById(R.id.mobilPhoto);
        Intent mIntent = getIntent();

        tvIdKatalog.setText(mIntent.getStringExtra("id_mobil"));
        tvNamaKatalog.setText(mIntent.getStringExtra("nama_mobil"));

        if (mIntent.getStringExtra("photo_mobil")!= null){
            Glide.with(getApplicationContext()).load(mIntent.getStringExtra("photo_mobil_url"))
                    .into(ivPhotoMobil);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.default_mobil).into(ivPhotoMobil);
        }

        getData();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobilListIntent = new Intent(getApplicationContext(), MobilListActivity.class);

                startActivity(mobilListIntent);
            }
        });

        btTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertReviewIntent = new Intent(getApplicationContext(), InsertReviewActivity.class);
                insertReviewIntent.putExtra("id_mobil", tvIdKatalog.getText().toString());
                insertReviewIntent.putExtra("nama_mobil", tvNamaKatalog.getText().toString());

                startActivity(insertReviewIntent);
            }
        });

    }

    private void getData() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody reqIdKatalog = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (tvIdKatalog.getText().toString()));

        Call<GetReviewMobil> mReviewanCall = mApiInterface.getMobil(reqIdKatalog);
        mReviewanCall.enqueue(new Callback<GetReviewMobil>() {
            @Override
            public void onResponse(Call<GetReviewMobil> call, Response<GetReviewMobil> response) {
                Log.d("Review List",response.body().getStatus());
                List<Review> listReview = response.body().getResult();
                mAdapter = new ReviewMobilAdapter(listReview);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetReviewMobil> call, Throwable t) {
                Log.d("Review List",t.getMessage());
            }
        });
    }
}