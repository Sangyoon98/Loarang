package com.cookandroid.loarang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class InfoFragment extends Fragment {

    EditText count; EditText exchange;
    TextView recommendPrice; TextView breakEvenPoint;
    TextView formatSendPrice; EditText price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        count = view.findViewById(R.id.count); // 인원수
        exchange = view.findViewById(R.id.exchange); // 거래소가격
        recommendPrice = view.findViewById(R.id.recommendPrice); // 입찰추천가
        breakEvenPoint = view.findViewById(R.id.breakEvenPoint); // 손익분기점
        formatSendPrice = view.findViewById(R.id.formatSendPrice); // 보낼 금액
        price = view.findViewById(R.id.price); // 받는 금액

        count.addTextChangedListener(auctionLoss);
        exchange.addTextChangedListener(auctionLoss);
        price.addTextChangedListener(transactionFee);

        return view;
    }

    private final TextWatcher auctionLoss = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (count.length() > 0 && exchange.length() > 0) {
                Double doubleCount = Double.parseDouble(count.getText().toString());
                Double doubleExchange = Double.parseDouble(exchange.getText().toString());
                Double doubleBreakEvenPoint = Math.floor((doubleExchange - Math.ceil(doubleExchange / 20)) * (doubleCount - 1) / doubleCount);
                Double doubleRecommendPrice = Math.floor(doubleBreakEvenPoint * 0.91);

                breakEvenPoint.setText(doubleBreakEvenPoint.toString());
                recommendPrice.setText(doubleRecommendPrice.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final TextWatcher transactionFee = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (price.length() > 0) {
                Double doublePrice = Double.parseDouble(price.getText().toString());
                Double doubleFormatSendPrice = Math.ceil(doublePrice * 1.0526315789);

                formatSendPrice.setText(doubleFormatSendPrice.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}