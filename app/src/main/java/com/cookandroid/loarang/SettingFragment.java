package com.cookandroid.loarang;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {
    Context context;
    Button notice, inquire, patch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        context = view.getContext();

        notice = view.findViewById(R.id.notice);
        inquire = view.findViewById(R.id.inquire);
        patch = view.findViewById(R.id.patch);

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SettingNotice.class);
                startActivity(intent);
            }
        });

        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("*/*");

                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_EMAIL, "qlrqod123123@gmail.com"); // 받는 사람 이메일
                intent.putExtra(Intent.EXTRA_SUBJECT, "문의할 주제를 입력하세요."); // 메일 제목
                intent.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 입력하세요."); // 메일 내용
                startActivity(intent);
            }
        });

        patch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SettingPatch.class);
                startActivity(intent);
            }
        });
        return view;
    }
}