package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osmaini.Api.ApiRequest;
import com.example.osmaini.Api.RetroClient;
import com.example.osmaini.Model.ResponseApiModel;
import com.example.osmaini.Model.UserModel;
import com.example.osmaini.Utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    CheckBox remember;
    private EditText edtusername, edtpassword;
    private Button login, regist;
    ProgressDialog pd;
    private static final String TAG = Login.class.getSimpleName();
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pd = new ProgressDialog(this);
        edtusername = (EditText) findViewById(R.id.username);
        edtpassword = (EditText) findViewById(R.id.password);
        remember = (CheckBox) findViewById(R.id.checkBox);
        login = (Button) findViewById(R.id.btnlogin);
        regist = (Button) findViewById(R.id.btnregist);
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
        else if (checkbox.equals("false")){
            Toast.makeText(this, "Silakan Masuk Terlebih Dahulu", Toast.LENGTH_LONG).show();
        }
        sm = new SessionManager(Login.this);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registrasi.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Sedang memuat masuk ... ");
                pd.setCancelable(false);
                pd.show();
                ApiRequest Api = RetroClient.getRequestService();
                Call<ResponseApiModel> login = Api.login(edtusername.getText().toString(), edtpassword.getText().toString());
                login.enqueue(new Callback<ResponseApiModel>() {
                    @Override
                    public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                        pd.hide();
                        Log.d(TAG, "response : " + response.toString());
                        ResponseApiModel res = response.body();
                        List<UserModel> user = res.getResult();
                        if (res.getKode().equals("1")){
                            sm.storeLogin(user.get(0).getId(),user.get(0).getUsername(),user.get(0).getRole(),user.get(0).getBidang_kode());
                            Toast.makeText(Login.this, "Username / Password Ditemukan login Sukses ! ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.putExtra("id",user.get(0).getId());
                            intent.putExtra("username",user.get(0).getUsername());
                            intent.putExtra("role",user.get(0).getRole());
                            intent.putExtra("bidang_kode",user.get(0).getBidang_kode());
                            startActivity(intent);
                        }
                        else {
                            pd.hide();
                            Toast.makeText(Login.this, "Username / Password tidak cocok", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                        Log.e(TAG,"error" + t.getMessage());
                    }
                });
            }
        });
    }
    public void onBackPressed(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Login.this);
        builder1.setMessage("Apakah Anda Yakin Untuk Keluar ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.super.onBackPressed();
                        finish();
                        moveTaskToBack(true);
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
