
//package com.bitspilani.bosmroulette.activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ProgressBar;
//
//import com.bitspilani.bosmroulette.R;
//
//public class HPC extends AppCompatActivity {
//
//    ProgressBar progress;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hpc);
//        final WebView webview = (WebView)findViewById(R.id.webview);
//        progress = (ProgressBar) findViewById(R.id.progressBar);
//        progress.setVisibility(View.GONE);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.setWebViewClient(new MyWebViewClient());
//
//        webview.loadUrl("https://hindipressclub.wordpress.com");
//    }
//}
//private class MyWebViewClient extends WebViewClient {
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        view.loadUrl(url);
//        return true;
//    }
//}

package com.bitspilani.bosmroulette.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bitspilani.bosmroulette.R;

public class HPC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hpc);
    }
}

