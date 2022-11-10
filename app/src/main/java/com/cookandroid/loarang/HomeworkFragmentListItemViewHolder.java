package com.cookandroid.loarang;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class HomeworkFragmentListItemViewHolder extends RecyclerView.ViewHolder {
    Context context;
    ImageView character_image;
    TextView character_nickname, character_level, character_class, character_itemLevel, character_server;
    CheckBox epona1, epona2, epona3, chaos_dungeon1, chaos_dungeon2, gadian_rade1, gadian_rade2, boss_rade1, boss_rade2, boss_rade3, abis_rade, abis_dungeon;

    public HomeworkFragmentListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        character_image = itemView.findViewById(R.id.character_image);
        character_nickname = itemView.findViewById(R.id.character_nickname);
        character_level = itemView.findViewById(R.id.character_level);
        character_class = itemView.findViewById(R.id.character_class);
        character_itemLevel = itemView.findViewById(R.id.character_itemLevel);
        character_server = itemView.findViewById(R.id.character_server);

        context = itemView.getContext();
        epona1 = itemView.findViewById(R.id.epona1);
        epona2 = itemView.findViewById(R.id.epona2);
        epona3 = itemView.findViewById(R.id.epona3);
        chaos_dungeon1 = itemView.findViewById(R.id.chaos_dungeon1);
        chaos_dungeon2 = itemView.findViewById(R.id.chaos_dungeon2);
        gadian_rade1 = itemView.findViewById(R.id.gadian_rade1);
        gadian_rade2 = itemView.findViewById(R.id.gadian_rade2);
        boss_rade1 = itemView.findViewById(R.id.boss_rade1);
        boss_rade2 = itemView.findViewById(R.id.boss_rade2);
        boss_rade3 = itemView.findViewById(R.id.boss_rade3);
        abis_rade = itemView.findViewById(R.id.abis_rade);
        abis_dungeon = itemView.findViewById(R.id.abis_dungeon);
    }

    public void onBind(HomeworkFragmentListItem homeworkFragmentListItem) {
        Glide.with(itemView).load(homeworkFragmentListItem.getCharacter_image())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(character_image);
        character_nickname.setText(homeworkFragmentListItem.getCharacter_nickname());
        //character_level.setText(homeworkFragmentListItem.getCharacter_level());
        //character_class.setText(homeworkFragmentListItem.getCharacter_class());
        character_itemLevel.setText(homeworkFragmentListItem.getCharacter_itemLevel());
        //character_server.setText(homeworkFragmentListItem.getCharacter_server());

        boolean boolean_epona1 = Boolean.parseBoolean(getPreferenceString("epona1" + getAdapterPosition()));
        epona1.setChecked(boolean_epona1);
        epona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_epona1 = epona1.isChecked();
                setPreference("epona1" + getAdapterPosition(), String.valueOf(boolean_epona1));
            }
        });

        boolean boolean_epona2 = Boolean.parseBoolean(getPreferenceString("epona2" + getAdapterPosition()));
        epona2.setChecked(boolean_epona2);
        epona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_epona2 = epona2.isChecked();
                setPreference("epona2" + getAdapterPosition(), String.valueOf(boolean_epona2));
            }
        });

        boolean boolean_epona3 = Boolean.parseBoolean(getPreferenceString("epona3" + getAdapterPosition()));
        epona3.setChecked(boolean_epona3);
        epona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_epona3 = epona3.isChecked();
                setPreference("epona3" + getAdapterPosition(), String.valueOf(boolean_epona3));
            }
        });

        boolean boolean_chaos_dungeon1 = Boolean.parseBoolean(getPreferenceString("chaos_dungeon1" + getAdapterPosition()));
        chaos_dungeon1.setChecked(boolean_chaos_dungeon1);
        chaos_dungeon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_chaos_dungeon1 = chaos_dungeon1.isChecked();
                setPreference("chaos_dungeon1" + getAdapterPosition(), String.valueOf(boolean_chaos_dungeon1));
            }
        });

        boolean boolean_chaos_dungeon2 = Boolean.parseBoolean(getPreferenceString("chaos_dungeon2" + getAdapterPosition()));
        chaos_dungeon2.setChecked(boolean_chaos_dungeon2);
        chaos_dungeon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_chaos_dungeon2 = chaos_dungeon2.isChecked();
                setPreference("chaos_dungeon2" + getAdapterPosition(), String.valueOf(boolean_chaos_dungeon2));
            }
        });

        boolean boolean_gadian_rade1 = Boolean.parseBoolean(getPreferenceString("gadian_rade1" + getAdapterPosition()));
        gadian_rade1.setChecked(boolean_gadian_rade1);
        gadian_rade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_gadian_rade1 = gadian_rade1.isChecked();
                setPreference("gadian_rade1" + getAdapterPosition(), String.valueOf(boolean_gadian_rade1));
            }
        });

        boolean boolean_gadian_rade2 = Boolean.parseBoolean(getPreferenceString("gadian_rade2" + getAdapterPosition()));
        gadian_rade2.setChecked(boolean_gadian_rade2);
        gadian_rade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_gadian_rade2 = gadian_rade2.isChecked();
                setPreference("gadian_rade2" + getAdapterPosition(), String.valueOf(boolean_gadian_rade2));
            }
        });

        boolean boolean_boss_rade1 = Boolean.parseBoolean(getPreferenceString("boss_rade1" + getAdapterPosition()));
        boss_rade1.setChecked(boolean_boss_rade1);
        boss_rade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_boss_rade1 = boss_rade1.isChecked();
                setPreference("boss_rade1" + getAdapterPosition(), String.valueOf(boolean_boss_rade1));
            }
        });

        boolean boolean_boss_rade2 = Boolean.parseBoolean(getPreferenceString("boss_rade2" + getAdapterPosition()));
        boss_rade2.setChecked(boolean_boss_rade2);
        boss_rade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_boss_rade2 = boss_rade2.isChecked();
                setPreference("boss_rade2" + getAdapterPosition(), String.valueOf(boolean_boss_rade2));
            }
        });

        boolean boolean_boss_rade3 = Boolean.parseBoolean(getPreferenceString("boss_rade3" + getAdapterPosition()));
        boss_rade3.setChecked(boolean_boss_rade3);
        boss_rade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_boss_rade3 = boss_rade3.isChecked();
                setPreference("boss_rade3" + getAdapterPosition(), String.valueOf(boolean_boss_rade3));
            }
        });

        boolean boolean_abis_rade = Boolean.parseBoolean(getPreferenceString("abis_rade" + getAdapterPosition()));
        abis_rade.setChecked(boolean_abis_rade);
        abis_rade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_abis_rade = abis_rade.isChecked();
                setPreference("abis_rade" + getAdapterPosition(), String.valueOf(boolean_abis_rade));
            }
        });

        boolean boolean_abis_dungeon = Boolean.parseBoolean(getPreferenceString("abis_dungeon" + getAdapterPosition()));
        abis_dungeon.setChecked(boolean_abis_dungeon);
        abis_dungeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_abis_dungeon = abis_dungeon.isChecked();
                setPreference("abis_dungeon" + getAdapterPosition(), String.valueOf(boolean_abis_dungeon));
            }
        });
    }

    public void setPreference(String key, String value){
        SharedPreferences pref = context.getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPreferenceString(String key) {
        SharedPreferences pref = context.getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}
