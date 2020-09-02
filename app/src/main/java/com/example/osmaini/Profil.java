package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.osmaini.Utils.SessionManager;

import java.util.HashMap;

public class Profil extends AppCompatActivity {
    TextView nama, role, domisili;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        sm = new SessionManager(Profil.this);

        sm.checkLogin();
        nama = (TextView) findViewById(R.id.nama);
        role  = (TextView) findViewById(R.id.role);
        domisili = (TextView) findViewById(R.id.domisili);

        HashMap<String, String> map = sm.getDetailLogin();
        nama.setText(map.get(sm.KEY_USERNAME));
        role.setText(map.get(sm.KEY_ROLE));
        domisili.setText(map.get(sm.KEY_BIDANG));
        final String tersadar = domisili.getText().toString();
        if (tersadar.equals("100")) {
            domisili.setText("Surabaya Barat");
        }
        else if (tersadar.equals("200")) {
            domisili.setText("Surabaya Selatan");
        }
        else if (tersadar.equals("300")) {
            domisili.setText("Surabaya Utara");
        }
        else if (tersadar.equals("400")) {
            domisili.setText("Surabaya Timur");
        }
        else {
            domisili.setText("Surabaya Tengah");
        }
        sm.checkLogin();
    }
}
