package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ozi.kmobil.Model.GetLogin;
import com.example.ozi.kmobil.Model.GetProfil;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyUserActivity extends AppCompatActivity {
    TextView tvUsername, tvNama;
    //String username, nama, alamat;
    ImageView ivPhoto;
    Button btEdit, btBack;
    ApiInterface mApiInterface;
    String id_user;
    String url_photo, photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvNama = (TextView) findViewById(R.id.tvNama);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btEdit = (Button) findViewById(R.id.btEdit);
        btBack = (Button) findViewById(R.id.btBack);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_user = preferences.getString("id_user","");
        getData();


        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), EditProfilActivity.class);
                mIntent.putExtra("id_user", id_user);
                mIntent.putExtra("nama_user", tvNama.getText().toString());
                mIntent.putExtra("username", tvUsername.getText().toString());
                mIntent.putExtra("photo_user", photoName);
                mIntent.putExtra("photo_user_url", url_photo);

                startActivity(mIntent);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent2);
            }
        });



    }

    private void getData() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody reqIdUser = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));

        Call<GetProfil> mMyProfileCall = mApiInterface.getProfil(reqIdUser);
        mMyProfileCall.enqueue(new Callback<GetProfil>() {
            @Override
            public void onResponse(Call<GetProfil> call, Response<GetProfil> response) {
                Log.d("MyProfile", response.body().getStatus());
                if (response.body().getStatus().equals("success")){
                    tvUsername.setText(response.body().getResult().get(0).getUsername());
                    tvNama.setText(response.body().getResult().get(0).getMobil());
                    url_photo = ApiClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getPhoto_user();
                    photoName = response.body().getResult().get(0).getPhoto_user();
                    if (photoName != null){
                        Glide.with(getApplicationContext()).load(url_photo).into(ivPhoto);
                    } else {
                        Glide.with(getApplicationContext()).load(R.drawable.default_user).into(ivPhoto);
                    }
                }else{
                    Toast.makeText(MyUserActivity.this, "Gagal mendapatkan profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfil> call, Throwable t) {
                Log.d("MyProfile", t.getMessage());
            }
        });
    }
}
