package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ozi.kmobil.Adapter.MyReviewAdapter;
import com.example.ozi.kmobil.Rest.ApiInterface;
import com.example.ozi.kmobil.Model.GetReviewMobil;
import com.example.ozi.kmobil.Model.GetReviewUser;
import com.example.ozi.kmobil.Model.Review;
import com.example.ozi.kmobil.Rest.ApiClient;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btBack;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btBack = (Button) findViewById(R.id.btBack);

        SharedPreferences sharedPreferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);

        id_user = sharedPreferences.getString("id_user","");

        getData();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, MainActivity.class);
                startActivity(mIntent);
            }
        });
    }

    private void getData() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody reqIdUser = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));

        Call<GetReviewUser> mReviewCall = mApiInterface.getUser(reqIdUser);
        mReviewCall.enqueue(new Callback<GetReviewUser>() {
            @Override
            public void onResponse(Call<GetReviewUser> call, Response<GetReviewUser> response) {
                Log.d("Review List",response.body().getStatus());
                List<Review> listReviewan = response.body().getResult();
                mAdapter = new MyReviewAdapter(listReviewan);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetReviewUser> call, Throwable t) {
                Log.d("Review List",t.getMessage());
            }
        });
    }
}
