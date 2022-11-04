package com.cookandroid.loarang;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingPatchListItemViewHolder extends RecyclerView.ViewHolder {
    TextView patch_name;
    TextView context_patch;

    public SettingPatchListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        patch_name = itemView.findViewById(R.id.patch_name);
        context_patch = itemView.findViewById(R.id.context_patch);
    }

    public void onBind(SettingPatchListItem settingPatchListItem) {
        patch_name.setText(settingPatchListItem.getName());
        context_patch.setText(settingPatchListItem.getContext_patch());
    }
}
