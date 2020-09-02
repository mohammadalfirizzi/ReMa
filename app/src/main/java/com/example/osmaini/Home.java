package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osmaini.Utils.SessionManager;

import java.util.HashMap;

public class Home extends AppCompatActivity {
    ImageView maps,toko,logout,datauser,keranjang,about;
    SessionManager sm;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sm = new SessionManager(Home.this);

        sm.checkLogin();
        maps = (ImageView) findViewById(R.id.maps);
        keranjang = (ImageView) findViewById(R.id.keranjang);
        toko = (ImageView) findViewById(R.id.toko);
        datauser = (ImageView) findViewById(R.id.datauser);
        logout = (ImageView) findViewById(R.id.logout);
        about = (ImageView) findViewById(R.id.about);
        username = (TextView) findViewById(R.id.hiya);
        HashMap<String, String> map = sm.getDetailLogin();
//        username.setText(map.get(sm.KEY_BIDANG));

        sm.checkLogin();

        datauser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profil.class);
                startActivity(intent);
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Peta.class);
                startActivity(intent);
            }
        });
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Toko.class);
                startActivity(intent);
            }
        });
        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, TabRema.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, About.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.logout();
                sm.checkLogin();
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}
