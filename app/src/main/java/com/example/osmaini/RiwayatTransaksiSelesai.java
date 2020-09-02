package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.osmaini.Api.ApiRequest;
import com.example.osmaini.Api.RetroClient;
import com.example.osmaini.Model.ListModel;
import com.example.osmaini.Model.ResponseReadModel;
import com.example.osmaini.Utils.SessionManager;
import com.example.osmaini.adapter.AdapterDataSelesai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatTransaksiSelesai extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView idcus;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<ListModel> mItems = new ArrayList<>();
    public SwipeRefreshLayout swipeRefreshLayout;
    final Handler handler = new Handler();
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi_selesai);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewtrf);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipee);
        swipeRefreshLayout.setOnRefreshListener(RiwayatTransaksiSelesai.this);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        sm = new SessionManager(RiwayatTransaksiSelesai.this);
        idcus = (TextView) findViewById(R.id.idcus);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        idcus.setText(map.get(sm.KEY_ID));
        idcus.setVisibility(View.INVISIBLE);
        sm.checkLogin();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        LoadIki();
                    }
                },3000);
            }
        });

        LoadIki();
    }
    public void LoadIki() {
        ApiRequest api = RetroClient.getClient().create(ApiRequest.class);

        Call<ResponseReadModel> getData = api.getRiwayatA(idcus.getText().toString());
        getData.enqueue(new Callback<ResponseReadModel>() {
            @Override
            public void onResponse(Call<ResponseReadModel> call, Response<ResponseReadModel> response) {
                Log.d("RETRO", "RESPONSE" +response.body().getKode());
                mItems = response.body().getResult();
                mAdapter = new AdapterDataSelesai(RiwayatTransaksiSelesai.this, mItems);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseReadModel> call, Throwable t) {
                Log.d("RETRO", "FAILED : Gagal");

            }
        });
    }

    @Override
    public void onRefresh() {
        LoadIki();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
