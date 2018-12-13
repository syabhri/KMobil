package com.example.ozi.kmobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ozi.kmobil.Model.GetLogin;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername;
    EditText txtPassword;
    ApiInterface mApiInterface;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btnLogin = findViewById(R.id.btn_login);
        this.txtUsername = findViewById(R.id.txt_username);
        this.txtPassword = findViewById(R.id.txt_password);

    }


    private void login() {
        String username = this.txtUsername.getText().toString();
        String password = this.txtPassword.getText().toString();

        this.checkCredentials(username, password);

    }

    private void openHome() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        this.startActivity(intent);
    }

    private void saveCredentials() {
        SharedPreferences handler = this.getSharedPreferences("data_login", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = handler.edit();

        editor.putString("username", this.txtUsername.getText().toString());
        editor.putString("password", this.txtPassword.getText().toString());
        editor.putString("id_user", this.id_user);
        editor.apply();

    }

    private void checkCredentials(String username, String password) {

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (username.toString().isEmpty())?"":username.toString());
        RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (password.toString().isEmpty())?"":password.toString());

        Call<GetLogin> mLoginCall = mApiInterface.postLogin(reqUsername, reqPassword);
        mLoginCall.enqueue(new Callback<GetLogin>() {
            @Override
            public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                Log.d("Login", response.body().getStatus()+" "+response.body().getResult());
                if (response.body().getStatus().equals("unketemu")){
                    Toast.makeText(LoginActivity.this, "Username atau password salah!", Toast.LENGTH_SHORT).show();
                }else{
                    id_user = response.body().getResult();
                    saveCredentials();
                    openHome();
                }
            }

            @Override
            public void onFailure(Call<GetLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Coba Lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void button_onClickLogin(View view) {
        this.login();
    }

}