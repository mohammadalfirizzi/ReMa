package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class TabRema extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_rema);
        TabHost th = getTabHost();
        TabHost.TabSpec ts;
        Intent i;

        i = new Intent().setClass(this, RiwayatTransaksi.class);
        ts = th.newTabSpec("Pending").setIndicator("Pending",null).setContent(i);
        th.addTab(ts);

        i = new Intent().setClass(this, RiwayatTransaksiSelesai.class);
        ts = th.newTabSpec("Selesai").setIndicator("Selesai",null).setContent(i);
        th.addTab(ts);



    }
}
