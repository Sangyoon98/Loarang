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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoFragment extends Fragment {

    TextView goldCal;TextView personNum;TextView minGold; TextView recomGold; TextView maxGold;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        goldCal = (TextView) view.findViewById(R.id.GoldCal);
        personNum = (TextView) view.findViewById(R.id.PersonNum);
        minGold = (TextView) view.findViewById(R.id.minGold);
        recomGold = (TextView) view.findViewById(R.id.recomGold);
        maxGold = (TextView) view.findViewById(R.id.maxGold);

        return view;
    }

    /*class infoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }*/
}