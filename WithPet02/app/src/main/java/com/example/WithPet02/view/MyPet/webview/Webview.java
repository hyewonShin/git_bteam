package com.example.WithPet02.view.MyPet.webview;

import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WithPet02.R;

public class Webview extends AppCompatActivity {
    WebView myWebView;
    String hospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        hospital = getIntent().getStringExtra("hospital");

        myWebView = findViewById(R.id.webview1);
        WebSettings myWebSettings = myWebView.getSettings();
        myWebSettings.setJavaScriptEnabled(true); // 자바스크립트 허용
        myWebSettings.setLoadWithOverviewMode(true); //스크린 모드 허용
        //GPS 자동으로 설정
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });


        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //myWebView.loadUrl("https://m.map.kakao.com/actions/routeView?startLoc=한울직업전문학교&sxEnc=LPRQTQ&syEnc=LQNNMN&endLoc=" + hospital +"&exEnc=&eyEnc=&ids=P22320373%2C&service=");
        myWebView.loadUrl("https://m.map.kakao.com/actions/routeView?startLoc=&sxEnc=LPRQTQ&syEnc=&endLoc=" + hospital +"&exEnc=LPMQNU&eyEnc=%2C&service=");

        //myWebView.loadUrl("https://m.map.kakao.com/actions/carRoute?startLoc=한울직업전문학교&sxEnc=LPRQTLHRQSRWPMRVRM&syEnc=LQNNMLHVYRMQMSRSS&endLoc=" + hospital + "&exEnc=LPMQNU&eyEnc=LQOOUQ&ids=%2C&service=");

        }


}