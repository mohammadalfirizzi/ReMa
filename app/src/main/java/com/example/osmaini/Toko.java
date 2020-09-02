package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Toko extends AppCompatActivity {
    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String HTTP_JSON_URL = "http://codemantul.my.id/GIS/androidapi/liat.php";

    String Image_Name_JSON = "dokumentasi_keterangan";

    String Image_URL_JSON = "dokumentasi_gambar";

    String Image_Kode = "dokumentasi_bidang_id";

    JsonArrayRequest RequestOfJSonArray ;

    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick;
    ArrayList<String> ImageUrlNameArrayListForClick;
    ArrayList<String> ImageKodeNameArrayListForClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        ImageTitleNameArrayListForClick = new ArrayList<>();
        ImageUrlNameArrayListForClick = new ArrayList<>();
        ImageKodeNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(Toko.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    // Showing RecyclerView Clicked Item value using Toast.
                    Toast.makeText(Toko.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Toko.this, TokoDua.class);
                    intent.putExtra("dokumentasi_gambar", ImageUrlNameArrayListForClick.get(RecyclerViewItemPosition));
                    intent.putExtra("dokumentasi_keterangan", ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition));
                    intent.putExtra("dokumentasi_bidang_id", ImageKodeNameArrayListForClick.get(RecyclerViewItemPosition));
                    startActivity(intent);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(Toko.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setDokumentasi_keterangan(json.getString(Image_Name_JSON));
                GetDataAdapter2.setDokumentasi_bidang_kode(json.getString(Image_Kode));

                // Adding image title name in array to display on RecyclerView click event.
                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                ImageUrlNameArrayListForClick.add(json.getString(Image_URL_JSON));
                ImageKodeNameArrayListForClick.add(json.getString(Image_Kode));

                GetDataAdapter2.setDokumentasi_gambar(json.getString(Image_URL_JSON));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
    }

