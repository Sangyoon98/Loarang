package com.cookandroid.loarang.ui.character;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.loarang.R;

public class CharacterFragmentDetail extends AppCompatActivity {
    WebView wView; // 웹뷰
    ProgressBar pBar; // 로딩바
    String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_character_detail);

        Intent intent = getIntent();
        String nickname = intent.getExtras().getString("nickname");
        URL = "https://lostark.game.onstove.com/Profile/Character/";
        URL += nickname;

        wView = findViewById(R.id.wView);   // 웹뷰
        pBar =  findViewById(R.id.pBar);    // 로딩바
        pBar.setVisibility(View.GONE);      // 로딩바 가리기 (로딩때만 보여야 함)

        initWebView();                      // 웹뷰 초기화
    }

    // 웹뷰 초기화 함수
    public void initWebView(){
        // 1. 웹뷰클라이언트 연결 (로딩 시작/끝 받아오기)
        wView.setWebViewClient(new WebViewClient(){
            @Override                                   // 1) 로딩 시작
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pBar.setVisibility(View.VISIBLE);       // 로딩이 시작되면 로딩바 보이기
            }
            @Override                                   // 2) 로딩 끝
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pBar.setVisibility(View.GONE);          // 로딩이 끝나면 로딩바 없애기
            }
            @Override                                   // 3) 외부 브라우저가 아닌 웹뷰 자체에서 url 호출
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 2. WebSettings: 웹뷰의 각종 설정을 정할 수 있다.
        WebSettings ws = wView.getSettings();
        ws.setJavaScriptEnabled(true); // 자바스크립트 사용 허가
        // 3. 웹페이지 호출
        wView.loadUrl(URL);
    }

    // 뒤로가기 동작 컨트롤
    @Override
    public void onBackPressed() {
        if(wView.canGoBack()){      // 이전 페이지가 존재하면
            wView.goBack();         // 이전 페이지로 돌아가고
        }else{
            super.onBackPressed();  // 없으면 앱 종료
        }
    }
}
