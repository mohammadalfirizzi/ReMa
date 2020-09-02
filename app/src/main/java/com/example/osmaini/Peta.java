package com.example.osmaini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Peta extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);

        myWebView = (WebView) findViewById(R.id.webview);
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Peta.this.setProgress(progress * 1000);
            }
        });
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://codemantul.my.id/GIS/");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    public void onBackPressed(){
        Intent intent = new Intent(Peta.this, Home.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
