package com.cookandroid.loarang;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoFragment extends Fragment {
    WebView info_web;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        info_web = view.findViewById(R.id.info_web);

        /* 이미지 정책 변경 후 조치
        if(Build.VERSION.SDK_INT >= 21) {
        sex
            info_web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        */
        info_web.getSettings().setJavaScriptEnabled(true);
        info_web.setWebViewClient(new infoWebViewClient());
        WebSettings webSet = info_web.getSettings();
        webSet.setBuiltInZoomControls(true);
        info_web.loadUrl("https://lostark.inven.co.kr/dataninfo/world/");
        info_web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == keyEvent.ACTION_DOWN){
                    if(i == keyEvent.KEYCODE_BACK) {
                        if(info_web!=null) {
                            if(info_web.canGoBack()) {
                                info_web.goBack();
                            }
                            else {
                                getActivity().onBackPressed();
                            }
                        }
                    }
                }
                return true;
            }
        });
        return view;
    }

    class infoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}