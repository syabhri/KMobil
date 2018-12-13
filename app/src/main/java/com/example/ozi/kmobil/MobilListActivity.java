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

import com.example.ozi.kmobil.Adapter.MobilListAdapter;
import com.example.ozi.kmobil.Model.GetMobil;
import com.example.ozi.kmobil.Model.Mobil;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobilListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil_list);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btBack = (Button) findViewById(R.id.btBack);

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
        Call<GetMobil> mMobilCall = mApiInterface.getMobil();
        mMobilCall.enqueue(new Callback<GetMobil>() {
            @Override
            public void onResponse(Call<GetMobil> call, Response<GetMobil> response) {
                Log.d("Katalog List",response.body().getStatus());
                List<Mobil> listMobil = response.body().getResult();
                mAdapter = new MobilListAdapter(listMobil);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetMobil> call, Throwable t) {
                Log.d("Katalog List",t.getMessage());
            }
        });
    }

}