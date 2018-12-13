package com.example.ozi.kmobil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ozi.kmobil.Model.GetEditUser;
import com.example.ozi.kmobil.Rest.ApiClient;
import com.example.ozi.kmobil.Rest.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilActivity extends AppCompatActivity {
    TextView tvUsername;
    EditText edtNama, edtAlamat;
    ImageView ivPhoto;
    Button btGaleri, btCamera, btEdit, btBack;
    String fileNamePhoto;
    String imagePath = "";
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtAlamat = (EditText) findViewById(R.id.edtAlamat);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btGaleri = (Button) findViewById(R.id.btGaleri);
        btCamera = (Button) findViewById(R.id.btCamera);
        btEdit = (Button) findViewById(R.id.btEdit);
        btBack = (Button) findViewById(R.id.btBack);

        Intent mIntent = getIntent();
        id_user = mIntent.getStringExtra("id_user");
        tvUsername.setText(mIntent.getStringExtra("username"));
        edtNama.setText(mIntent.getStringExtra("nama_user"));
        edtAlamat.setText(mIntent.getStringExtra("alamat_user"));
        fileNamePhoto = mIntent.getStringExtra("photo_user");
        imagePath = mIntent.getStringExtra("photo_user_url");
        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(imagePath).into(ivPhoto);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.default_user).into(ivPhoto);
        }

        imagePath = mIntent.getStringExtra("photo_user_url");

        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!imagePath.contains("uploads/" + fileNamePhoto)) &&
                        (imagePath.length()>0)){
                    //File creating from selected URL
                    File file = new File(imagePath);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("photo_user", file.getName(),
                            requestFile);
                }

                RequestBody reqIdUser =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (id_user));

                RequestBody reqNama =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtNama.getText().toString().isEmpty())?
                                        "" : edtNama.getText().toString());

                RequestBody reqAlamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAlamat.getText().toString().isEmpty())?
                                        "" : edtAlamat.getText().toString());


                Call<GetEditUser> callUpdate = mApiInterface.postEditUser(body, reqIdUser, reqNama,
                        reqAlamat);

                callUpdate.enqueue(new Callback<GetEditUser>() {
                    @Override
                    public void onResponse(Call<GetEditUser> call, Response<GetEditUser> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditProfilActivity.this, "Gagal Edit Profil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditProfilActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myProfileIntent = new Intent(getApplicationContext(), MyUserActivity.class);
                            startActivity(myProfileIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetEditUser> call, Throwable t) {
                        //Log.d("Update Retrofit ", t.getMessage());
                        Toast.makeText(EditProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


        btGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih foto untuk " +
                        "di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDeviceSupportCamera()) {
                    Toast.makeText(getApplicationContext(),"Camera di device anda tidak tersedia",
                            Toast.LENGTH_LONG).show();
                    Log.d("Camera", "gak ada kamera!!");
                    finish();
                } else {
                    Log.d("Camera", "ada kamera");
                    captureImage();

                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfileIntent = new Intent(getApplicationContext(), MyUserActivity.class);
                startActivity(myProfileIntent);
            }
        });

    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // requestCode 100 untuk membedakan
            startActivityForResult(takePictureIntent, 100);
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Ok, device punya camera
            return true;
        } else {
            // Device masih mendol
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            } else {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.
                            Media.getBitmap(this.getContentResolver(), contentURI);
                    imagePath = simpanImage(bitmap);
                    Toast.makeText(getApplicationContext(), "Foto berhasil di-load!",
                            Toast.LENGTH_SHORT).show();

                    ivPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (resultCode == RESULT_OK && requestCode == 100) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(thumbnail);

            imagePath = simpanImage(thumbnail);
            Toast.makeText(getApplicationContext(), "Foto berhasil di-load dari Camera!",
                    Toast.LENGTH_SHORT).show();


        }
    }

    public String simpanImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/ngopiyuk");

        // Buat direktori untuk penyimpanan
        if (!lokasiImage.exists()) {
            lokasiImage.mkdirs();
        }

        try {
            // Untuk penamaan file
            File f = new File(lokasiImage, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();

            // Operasi file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.d("Foto", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


}
