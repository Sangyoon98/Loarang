package com.cookandroid.loarang;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingNoticeListViewHolder extends RecyclerView.ViewHolder {
    TextView notice_name;
    TextView context_notice;

    public SettingNoticeListViewHolder(@NonNull View itemView) {
        super(itemView);

        notice_name = itemView.findViewById(R.id.notice_name);
        context_notice = itemView.findViewById(R.id.context_notice);
    }

    public void onBind(SettingNoticeListItem settingNoticeListItem) {
        notice_name.setText(settingNoticeListItem.getName());
        context_notice.setText(settingNoticeListItem.getContext_notice());
    }
}
