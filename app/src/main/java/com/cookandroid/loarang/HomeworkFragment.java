package com.cookandroid.loarang;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeworkFragment extends Fragment {
    Context context;
    RecyclerView listView_homework;
    HomeworkFragmentListItemAdapter adapter;
    HomeworkFragmentListItemViewHolder holder;
    HomeworkFragmentListItem listItem;
    ArrayList<HomeworkFragmentListItem> mArrayList = new ArrayList<>();
    private DBHelper mDbHelper;
    private SQLiteDatabase db;
    CheckBox chaos_gate, field_boss, adventure_island, dogato1, dogato2, dobis1, dobis2;
    static Button today_delete, all_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);

        chaos_gate = view.findViewById(R.id.chaos_gate);
        field_boss = view.findViewById(R.id.field_boss);
        adventure_island = view.findViewById(R.id.adventure_island);
        dogato1 = view.findViewById(R.id.dogato1);
        dogato2 = view.findViewById(R.id.dogato2);
        dobis1 = view.findViewById(R.id.dobis1);
        dobis2 = view.findViewById(R.id.dobis2);
        today_delete = view.findViewById(R.id.today_delete);
        all_delete = view.findViewById(R.id.all_delete);

        context = view.getContext();
        listView_homework = view.findViewById(R.id.listView_homework);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listView_homework.setLayoutManager(linearLayoutManager);
        adapter = new HomeworkFragmentListItemAdapter();

        //DBHelper 객체를 선언해줍니다.
        mDbHelper = new DBHelper(context);
        //쓰기모드에서 데이터 저장소를 불러옵니다.
        db = mDbHelper.getWritableDatabase();

        OnCreateBackgroundTask();

        today_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreference("chaos_gate", "false");
                setPreference("field_boss", "false");
                setPreference("adventure_island", "false");
                chaos_gate.setChecked(false);
                field_boss.setChecked(false);
                adventure_island.setChecked(false);

                for (int pos = 0; pos <= adapter.getItemCount(); pos++){
                    setPreference("epona1" + pos, "false");
                    setPreference("epona2" + pos, "false");
                    setPreference("epona3" + pos, "false");
                    setPreference("chaos_dungeon1" + pos, "false");
                    setPreference("chaos_dungeon2" + pos, "false");
                    setPreference("gadian_rade1" + pos, "false");
                    setPreference("gadian_rade2" + pos, "false");
                }
                adapter.notifyDataSetChanged();
            }
        });

        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreference("chaos_gate", "false");
                setPreference("field_boss", "false");
                setPreference("adventure_island", "false");
                setPreference("dogato1", "false");
                setPreference("dogato2", "false");
                setPreference("dobis1", "false");
                setPreference("dobis2", "false");
                chaos_gate.setChecked(false);
                field_boss.setChecked(false);
                adventure_island.setChecked(false);
                dogato1.setChecked(false);
                dogato2.setChecked(false);
                dobis1.setChecked(false);
                dobis2.setChecked(false);

                for (int pos = 0; pos <= adapter.getItemCount(); pos++){
                    setPreference("epona1" + pos, "false");
                    setPreference("epona2" + pos, "false");
                    setPreference("epona3" + pos, "false");
                    setPreference("chaos_dungeon1" + pos, "false");
                    setPreference("chaos_dungeon2" + pos, "false");
                    setPreference("gadian_rade1" + pos, "false");
                    setPreference("gadian_rade2" + pos, "false");
                    setPreference("boss_rade1" + pos, "false");
                    setPreference("boss_rade2" + pos, "false");
                    setPreference("boss_rade3" + pos, "false");
                    setPreference("abis_rade" + pos, "false");
                    setPreference("abis_dungeon" + pos, "false");
                }
                adapter.notifyDataSetChanged();
            }
        });

        boolean boolean_chaos_gate = Boolean.parseBoolean(getPreferenceString("chaos_gate"));
        chaos_gate.setChecked(boolean_chaos_gate);
        chaos_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_chaos_gate = chaos_gate.isChecked();
                setPreference("chaos_gate", String.valueOf(boolean_chaos_gate));
            }
        });

        boolean boolean_field_boss = Boolean.parseBoolean(getPreferenceString("field_boss"));
        field_boss.setChecked(boolean_field_boss);
        field_boss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_field_boss = field_boss.isChecked();
                setPreference("field_boss", String.valueOf(boolean_field_boss));
            }
        });

        boolean boolean_adventure_island = Boolean.parseBoolean(getPreferenceString("adventure_island"));
        adventure_island.setChecked(boolean_adventure_island);
        adventure_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_adventure_island = adventure_island.isChecked();
                setPreference("adventure_island", String.valueOf(boolean_adventure_island));
            }
        });

        boolean boolean_dogato1 = Boolean.parseBoolean(getPreferenceString("dogato1"));
        dogato1.setChecked(boolean_dogato1);
        dogato1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_dogato1 = dogato1.isChecked();
                setPreference("dogato1", String.valueOf(boolean_dogato1));
            }
        });

        boolean boolean_dogato2 = Boolean.parseBoolean(getPreferenceString("dogato2"));
        dogato2.setChecked(boolean_dogato2);
        dogato2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_dogato2 = dogato2.isChecked();
                setPreference("dogato2", String.valueOf(boolean_dogato2));
            }
        });

        boolean boolean_dobis1 = Boolean.parseBoolean(getPreferenceString("dobis1"));
        dobis1.setChecked(boolean_dobis1);
        dobis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_dobis1 = dobis1.isChecked();
                setPreference("dobis1", String.valueOf(boolean_dobis1));
            }
        });

        boolean boolean_dobis2 = Boolean.parseBoolean(getPreferenceString("dobis2"));
        dobis2.setChecked(boolean_dobis2);
        dobis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean boolean_dobis2 = dobis2.isChecked();
                setPreference("dobis2", String.valueOf(boolean_dobis2));
            }
        });

        return view;
    }

    //BackgroundTask
    Disposable onCreateBackgroundTask;
    void OnCreateBackgroundTask() {
        //onPreExecute

        onCreateBackgroundTask = Observable.fromCallable(() -> {
            //doInBackground
            @SuppressLint("Recycle") Cursor c = db.rawQuery ("SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME, null);
            while (c.moveToNext ()) {
                @SuppressLint("Range") String img_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_IMAGE));
                @SuppressLint("Range") String name_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_NAME));
                @SuppressLint("Range") String charLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL));
                @SuppressLint("Range") String class_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CLASS));
                @SuppressLint("Range") String itemLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL));
                @SuppressLint("Range") String server_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_SERVER));
                listItem = new HomeworkFragmentListItem(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
                adapter.addItem(listItem);
                mArrayList.add(listItem);
                Log.d("loggg", img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result);
            }
            listView_homework.setAdapter(adapter);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
            //onPostExecute
            onCreateBackgroundTask.dispose();
        }, throwable -> System.out.println("Error"));
    }

    public void setPreference(String key, String value){
        SharedPreferences pref = this.getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}