package com.cookandroid.loarang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.loarang.ui.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CharacterFragment extends Fragment {
    public static String TAG = "CharacterFragment";
    Context context;
    FloatingActionButton addCharBtn;
    RecyclerView listView;
    CharacterFragmentListItemAdapter adapter;
    CharacterFragmentListItem listItem;
    String nickname = "https://lostark.game.onstove.com/Profile/Character/";
    private DBHelper mDbHelper;
    private SQLiteDatabase db;
    ArrayList<CharacterFragmentListItem> mArrayList = new ArrayList<>();

    @NotNull
    public static Fragment newInstance() {
        return new CharacterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        context = view.getContext();
        addCharBtn = view.findViewById(R.id.addCharBtn);
        listView = view.findViewById(R.id.listView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(linearLayoutManager);
        adapter = new CharacterFragmentListItemAdapter();

        //DBHelper 객체를 선언해줍니다.
        mDbHelper = new DBHelper(context);
        //쓰기모드에서 데이터 저장소를 불러옵니다.
        db = mDbHelper.getWritableDatabase();

        OnCreateBackgroundTask();

        addCharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText dlgEdt = new EditText(getActivity());
                nickname = "https://lostark.game.onstove.com/Profile/Character/";
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("캐릭터 검색");
                dlg.setView(dlgEdt);
                dlg.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dlgEdt.getText().toString().equals("")) {
                            Toast.makeText(context, "닉네임을 다시 입력해주세요." , Toast.LENGTH_SHORT).show();
                        } else {
                            nickname += dlgEdt.getText().toString();
                            AddCharacter(nickname);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, dlgEdt.getText().toString() + "의 정보를 추가하였습니다." , Toast.LENGTH_SHORT).show();
                            //OnRecreateBackgroundTask();
                        }
                    }
                });
                dlg.show();
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
                listItem = new CharacterFragmentListItem(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
                adapter.addItem(listItem);
                mArrayList.add(listItem);
                Log.d("loggg", img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result);
            }
            listView.setAdapter(adapter);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
            //onPostExecute
            onCreateBackgroundTask.dispose();
            adapter.notifyDataSetChanged();
        }, throwable -> System.out.println("Error"));
    }

    //ReCreateBackgroundTask
    Disposable onRecreateBackgroundTask;
    void OnRecreateBackgroundTask() {
        //onPreExecute
        //adapter.removeItem();
        onRecreateBackgroundTask = Observable.fromCallable(() -> {
            //doInBackground
            @SuppressLint("Recycle") Cursor c = db.rawQuery ("SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME, null);
            while (c.moveToNext ()) {
                @SuppressLint("Range") String img_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_IMAGE));
                @SuppressLint("Range") String name_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_NAME));
                @SuppressLint("Range") String charLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL));
                @SuppressLint("Range") String class_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CLASS));
                @SuppressLint("Range") String itemLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL));
                @SuppressLint("Range") String server_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_SERVER));
                listItem = new CharacterFragmentListItem(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
                adapter.addItem(listItem);
                mArrayList.add(listItem);
                Log.d("loggg", img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result);
            }
            listView.setAdapter(adapter);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
            //onPostExecute
            onRecreateBackgroundTask.dispose();
            adapter.notifyDataSetChanged();
        }, throwable -> System.out.println("Error"));
    }

    //BackgroundTask
    Disposable addCharacter;
    void AddCharacter(String URLs) {
        //onPreExecute

        boolean isConnected = isNetworkConnected(context);
        if (!isConnected)
        {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle("알림")
                    .setMessage("네트워크 연결 상태를 확인해 주세요.")
                    .setPositiveButton("확인", null)
                    .create()
                    .show();
        } else {
            addCharacter = Observable.fromCallable(() -> {
                //doInBackground
                String name_result = "";
                String server_result = "";
                String charLevel_result = "";
                String itemLevel_result = "";
                String class_result = "";
                String img_result = "";

                try {
                    Document document = Jsoup.connect(URLs).get();
                    Elements name_elements = document.select("span[class=profile-character-info__name]");
                    for (Element element : name_elements) {
                        name_result = name_result + element.text();
                    }
                    Elements server_elements = document.select("span[class=profile-character-info__server]");
                    for (Element element : server_elements) {
                        server_result = server_result + element.text();
                    }
                    Elements charLevel_elements = document.select("span[class=profile-character-info__lv]");
                    for (Element element : charLevel_elements) {
                        charLevel_result = charLevel_result + element.text();
                    }
                    Elements itemLevel_elements = document.select("div[class=level-info2__expedition]");
                    for (Element element : itemLevel_elements) {
                        itemLevel_result = itemLevel_result + element.text();
                    }
                    String[] itemLevel_result_split = itemLevel_result.split("벨");
                    itemLevel_result = itemLevel_result_split[1];
                    Elements class_elements = document.select("img[class=profile-character-info__img]");
                    class_result = class_elements.attr("alt");
                    Elements img_elements = document.select("img[class=profile-character-info__img]");
                    img_result = img_elements.attr("src");

                    //데이터를 테이블에 삽입합니다.
                    insertNumber(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
                    Intent in = new Intent(context, MainActivity.class);
                    startActivity(in);
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((listItem) -> {
                //onPostExecute
                addCharacter.dispose();
            }, throwable -> System.out.println("Error"));
        }
    }

    //SQLite 데이터 삽입
    private void insertNumber(String img_result, String name_result, String charLevel_result, String class_result, String itemLevel_result, String server_result){
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_NAME_IMAGE, img_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_NAME, name_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL, charLevel_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CLASS, class_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL, itemLevel_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_SERVER, server_result);
        db.insert(DBHelper.FeedEntry.TABLE_NAME, null, values);
    }

    //SQLite 데이터 수정
    /* 사용하지 않음
    private void updateNumber(String old_img_result, String old_name_result, String old_charLevel_result, String old_class_result, String old_itemLevel_result, String old_server_result, String new_img_result, String new_name_result, String new_charLevel_result, String new_class_result, String new_itemLevel_result, String new_server_result){
        //수정된 값들을 values 에 추가한다.
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_NAME_IMAGE, new_img_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_NAME, new_name_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL, new_charLevel_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_CLASS, new_class_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL, new_itemLevel_result);
        values.put (DBHelper.FeedEntry.COLUMN_NAME_SERVER, new_server_result);

        // WHERE 절 수정될 열을 찾는다.
        String selection = DBHelper.FeedEntry.COLUMN_NAME_IMAGE + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_NAME + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_CLASS + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL + " LIKE ?" +
                " AND "+ DBHelper.FeedEntry.COLUMN_NAME_SERVER + " LIKE ?";
        String[] selectionArgs = {old_img_result, old_name_result, old_charLevel_result, old_class_result, old_itemLevel_result, old_server_result};
        db.update(DBHelper.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }
     */

    //SQLite 데이터 삭제
    private void deleteNumber(String img_result, String name_result, String charLevel_result, String class_result, String itemLevel_result, String server_result) {
        //WHERE 절 삭제될 열을 찾는다.
        String selection = DBHelper.FeedEntry.COLUMN_NAME_IMAGE + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_NAME + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_CLASS + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL + " LIKE ?" +
                " and " + DBHelper.FeedEntry.COLUMN_NAME_SERVER + " LIKE ?";
        //삭제될 열을 찾을 데이터
        String[] selectionArgs = {img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result};
        db.delete(DBHelper.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    //인터넷 연결 확인
    public boolean isNetworkConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        @SuppressLint("MissingPermission") NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        @SuppressLint("MissingPermission") NetworkInfo wimax = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean bwimax = false;
        if (wimax != null) {
            bwimax = wimax.isConnected();
        }
        if (mobile != null) {
            if (mobile.isConnected() || wifi.isConnected() || bwimax) {
                return true;
            }
        } else {
            if (wifi.isConnected() || bwimax) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final int position = adapter.getPosition();
        String img_result = mArrayList.get(position).getCharacter_image();
        String name_result = mArrayList.get(position).getCharacter_nickname();
        String charLevel_result = mArrayList.get(position).getCharacter_level();
        String class_result = mArrayList.get(position).getCharacter_class();
        String itemLevel_result = mArrayList.get(position).getCharacter_itemLevel();
        String server_result = mArrayList.get(position).getCharacter_server();

        int itemId = item.getItemId();
        if (itemId == R.id.character_update) {
            Toast.makeText(context, name_result + "의 정보를 갱신하였습니다.", Toast.LENGTH_SHORT).show();
            mArrayList.remove(position);
            deleteNumber(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
            nickname = "https://lostark.game.onstove.com/Profile/Character/";
            nickname += name_result;
            AddCharacter(nickname);
            //OnRecreateBackgroundTask();
        } else if (itemId == R.id.character_delete) {
            Toast.makeText(context, name_result + "의 정보를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
            mArrayList.remove(position);
            deleteNumber(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
            //OnRecreateBackgroundTask();
        }
        Intent in = new Intent(context, MainActivity.class);
        startActivity(in);
        return true;
    }
}