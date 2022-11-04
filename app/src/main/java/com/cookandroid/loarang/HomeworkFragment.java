package com.cookandroid.loarang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeworkFragment extends Fragment {
    Context context;
    RecyclerView listView_homework;
    HomeworkFragmentListItemAdapter adapter;
    HomeworkFragmentListItem listItem;
    ArrayList<HomeworkFragmentListItem> mArrayList = new ArrayList<>();
    private DBHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);

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
}