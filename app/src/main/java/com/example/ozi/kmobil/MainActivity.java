package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.example.ozi.kmobil.Model.Login;

public class MainActivity extends AppCompatActivity {

    RelativeLayout listMobil, listMyReview, myProfile, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMobil = findViewById(R.id.relativeListKatalog);
        listMyReview = findViewById(R.id.relativeMyReview);
        myProfile = findViewById(R.id.relativeMyProfile);
        logout = findViewById(R.id.relativeLogout);

        listMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), MobilListActivity.class);
                startActivity(mIntent);
            }
        });

        listMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(mIntent);
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), MyUserActivity.class);
                startActivity(mIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.apply();


                Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
