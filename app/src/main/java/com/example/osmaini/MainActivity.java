package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.texttt);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            SharedPreferences s = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("remember","true");
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Peta.class);
            startActivity(intent);
        }
        else if (checkbox.equals("false")){
        }
    }
    public void progressAnimation(){
        ProgressBar_Animation anim = new ProgressBar_Animation(this,progressBar, textView, 0f, 100f);
        anim.setDuration(8000);
        progressBar.setAnimation(anim);


    }
}
