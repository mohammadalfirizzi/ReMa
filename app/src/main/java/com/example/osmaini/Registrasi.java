package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osmaini.Api.ApiRequest;
import com.example.osmaini.Api.RetroClient;
import com.example.osmaini.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrasi extends AppCompatActivity {
    TextView namajeniskelas;
    EditText username, password, c_pass;
    Button btnsave;
    RadioGroup kelas;
    RadioButton kelas1,kelas2,kelas3,kelas4,kelas5;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        pd = new ProgressDialog(this);
        namajeniskelas = (TextView) findViewById(R.id.namajeniskelas);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        c_pass = (EditText) findViewById(R.id.password2);
        btnsave = (Button) findViewById(R.id.btn_insertdata);
        kelas = (RadioGroup) findViewById(R.id.kelas);
        kelas1 = (RadioButton) findViewById(R.id.kelas1);
        kelas2 = (RadioButton) findViewById(R.id.kelas2);
        kelas3 = (RadioButton) findViewById(R.id.kelas3);
        kelas4 = (RadioButton) findViewById(R.id.kelas4);
        kelas5 = (RadioButton) findViewById(R.id.kelas5);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Sedang memuat masuk ... ");
                pd.setCancelable(false);
                pd.show();
                int selectedId3 = kelas.getCheckedRadioButtonId();
                String skelas = namajeniskelas.getText().toString();
                if (selectedId3 == kelas1.getId()){
                    skelas = kelas1.getText().toString();
                } else if (selectedId3 == kelas2.getId()) {
                    skelas = kelas2.getText().toString();
                }
                else if (selectedId3 == kelas3.getId()) {
                    skelas = kelas3.getText().toString();
                }
                else if (selectedId3 == kelas4.getId()) {
                    skelas = kelas4.getText().toString();
                }
                else if (selectedId3 == kelas5.getId()) {
                    skelas = kelas5.getText().toString();
                }
                else {
                    Toast.makeText(Registrasi.this, "Belum memilih", Toast.LENGTH_SHORT).show();
                }
                String susername = username.getText().toString();
                String spassword = password.getText().toString();
                String scpass = c_pass.getText().toString();

                ApiRequest api = RetroClient.getClient().create(ApiRequest.class);

                retrofit2.Call<ResponseModel> savedata = api.sendBiodata(susername, skelas, spassword, scpass);
                savedata.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if(kode.equals("1"))
                        {
                            Toast.makeText(Registrasi.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                        else if (kode.equals("3")){
                            Toast.makeText(Registrasi.this, "Data anda tidak cocok", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Registrasi.this, "Data Error tidak berhasil disimpan", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                    }
                });
            }
        });

    }
}
