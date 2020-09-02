package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osmaini.Api.ApiRequest;
import com.example.osmaini.Api.RetroClient;
import com.example.osmaini.Model.ResponseReadModel;
import com.example.osmaini.Utils.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokoDua extends AppCompatActivity {

    ImageView load;
    TextView keterangan,uri,kode,kode2,id_user,uang, id_pembayaran;
    SessionManager sm;
    Button bayar;
    private static final String TAG = TokoDua.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_dua);
        sm = new SessionManager(TokoDua.this);

        sm.checkLogin();
        keterangan = (TextView) findViewById(R.id.ImageNameTextView);
        uri = (TextView) findViewById(R.id.uri);
        kode = (TextView) findViewById(R.id.kode);
        kode2 = (TextView) findViewById(R.id.kode2);
        id_user = (TextView) findViewById(R.id.id_user);
        id_pembayaran = (TextView) findViewById(R.id.id_pembayaran);
        uang = (TextView) findViewById(R.id.uang);
        bayar = (Button) findViewById(R.id.bayar);
//        load = (ImageView) findViewById(R.id.VolleyImageView);
        HashMap<String, String> map = sm.getDetailLogin();
        id_user.setText(map.get(sm.KEY_ID));
        kode.setText(map.get(sm.KEY_BIDANG));

        Random random1 = new Random();
        String generateid = String.format("%08d", random1.nextInt(100000000));
        id_pembayaran.setText(generateid);
        final String shasilid = id_pembayaran.getText().toString();
        String sketerangan = keterangan.getText().toString();
        String suri = uri.getText().toString();
        String skode2 = kode2.getText().toString();
        final int suang = 35000;
        uang.setText("Rp "+suang);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                sketerangan = null;
                suri = null;
                skode2 = null;
                keterangan.setText(sketerangan);
                uri.setText(suri);
                kode2.setText(skode2);
            }
            else {
                sketerangan = bundle.getString("dokumentasi_keterangan");
                suri = bundle.getString("dokumentasi_gambar");
                skode2 = bundle.getString("dokumentasi_bidang_id");
                keterangan.setText(sketerangan);
                uri.setText(suri);
                kode2.setText(skode2);
            }
        }
        else {
            sketerangan = (String) savedInstanceState.getSerializable("dokumentasi_keterangan");
            suri = (String) savedInstanceState.getSerializable("dokumentasi_gambar");
            skode2 = (String) savedInstanceState.getSerializable("dokumentasi_bidang_id");
            keterangan.setText(sketerangan);
            uri.setText(suri);
            kode2.setText(skode2);
        }
        uri.setVisibility(View.INVISIBLE);
        new DownloadImageFromInternet((ImageView) findViewById(R.id.VolleyImageView))
                .execute(suri);

        final String kk = kode.getText().toString();
        final String finalSkode = skode2;
        kode.setVisibility(View.INVISIBLE);
        kode2.setVisibility(View.INVISIBLE);
        id_user.setVisibility(View.INVISIBLE);
        id_pembayaran.setVisibility(View.INVISIBLE);
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(TokoDua.this);
                builder1.setMessage("Apakah Anda Yakin Untuk Membeli ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (finalSkode.equals(kk)) {
                                    Toast.makeText(TokoDua.this, "Berhasil", Toast.LENGTH_LONG).show();
                                    ApiRequest api = RetroClient.getClient().create(ApiRequest.class);
                                    Call<ResponseReadModel> sendBayar = api.sendTopup(shasilid,id_user.getText().toString(), String.valueOf(suang));
                                    sendBayar.enqueue(new Callback<ResponseReadModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseReadModel> call, Response<ResponseReadModel> response) {
                                            Log.d(TAG, "response : " + response.toString());
                                            String kode = response.body().getKode();
                                            if (kode.equals("1")) {
                                                Toast.makeText(TokoDua.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(TokoDua.this, Upload.class);
                                                intent.putExtra("id_pembayaran", shasilid);
                                                startActivity(intent);
                                            }
                                            else if (kode.equals("2")){
                                                Toast.makeText(TokoDua.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseReadModel> call, Throwable t) {

                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(TokoDua.this, "Tidak Berhasil", Toast.LENGTH_LONG).show();
                                }
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
        });
    }
    }
    class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

