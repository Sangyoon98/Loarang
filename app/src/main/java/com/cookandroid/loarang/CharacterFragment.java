package com.cookandroid.loarang;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
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
    Context context;
    TextView addCharText;
    FloatingActionButton addCharBtn;
    RecyclerView listView;
    CharacterFragmentListItemAdapter adapter;
    CharacterFragmentListItem listItem;
    String nickname = "https://lostark.game.onstove.com/Profile/Character/";
    private DBHelper mDbHelper;
    private SQLiteDatabase db;
    CharacterFragmentListItem mListItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        context = view.getContext();
        addCharText = view.findViewById(R.id.addCharText);
        addCharBtn = view.findViewById(R.id.addCharBtn);
        listView = view.findViewById(R.id.listView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(linearLayoutManager);
        adapter = new CharacterFragmentListItemAdapter();

        //DBHelper 객체를 선언해줍니다.
        mDbHelper = new DBHelper (context);
        //쓰기모드에서 데이터 저장소를 불러옵니다.
        db = mDbHelper.getWritableDatabase ();

        addCharText.setVisibility(view.GONE);
        listView.setVisibility(view.VISIBLE);
        OnCreateBackgroundTask();
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();

        addCharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText dlgEdt = new EditText(getActivity());
                nickname = "https://lostark.game.onstove.com/Profile/Character/";
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("캐릭터 검색");
                dlg.setView(dlgEdt);
                dlg.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nickname += dlgEdt.getText().toString();
                        addCharText.setVisibility(view.GONE);
                        listView.setVisibility(view.VISIBLE);
                        BackgroundTask(nickname);
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
            loadData();
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
            //onPostExecute

            onCreateBackgroundTask.dispose();
        }, throwable -> System.out.println("Error"));
    }

    //BackgroundTask
    Disposable backgroundTask;
    void BackgroundTask(String URLs) {
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
            backgroundTask = Observable.fromCallable(() -> {
                //doInBackground
                String name_result = "";
                String server_result = "";
                String charLevel_result = "";
                String itemLevel_result = "";
                String class_result = "";
                String img_result = "";

                try {
                    Document document = Jsoup.connect(nickname).get();
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
                    listItem = new CharacterFragmentListItem(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
                    return listItem;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((listItem) -> {
                //onPostExecute
                adapter.addItem(listItem);
                listView.setAdapter(adapter);
                backgroundTask.dispose();
            }, throwable -> System.out.println("Error"));
        }
    }

    //데이터 삽입
    private void insertNumber(String img_result, String name_result, String charLevel_result, String class_result, String itemLevel_result, String server_result){
        //쿼리를 직접 작성해서 입력하거나 values를 만들어서 하는 방법이 있다
        //후자를 이용하겠다.
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_NAME_IMAGE, img_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_NAME, name_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL, charLevel_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CLASS, class_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL, itemLevel_result);
        values.put(DBHelper.FeedEntry.COLUMN_NAME_SERVER, server_result);
        db.insert(DBHelper.FeedEntry.TABLE_NAME, null, values);
    }

    //데이터 불러오기
    //Cursor를 사용해서 데이터를 불러옵니다.
    //while문을 사용해서 불러온 데이터를 mArrayList에 삽입합니다.
    private void loadData() {
        @SuppressLint("Recycle") Cursor c = db.rawQuery ("SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME, null);
        while (c.moveToNext ()) {
            @SuppressLint("Range") String img_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_IMAGE));
            @SuppressLint("Range") String name_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_NAME));
            @SuppressLint("Range") String charLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CHARACTER_LEVEL));
            @SuppressLint("Range") String class_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_CLASS));
            @SuppressLint("Range") String itemLevel_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_ITEM_LEVEL));
            @SuppressLint("Range") String server_result = c.getString (c.getColumnIndex (DBHelper.FeedEntry.COLUMN_NAME_SERVER));
            mListItem = new CharacterFragmentListItem(img_result, name_result, charLevel_result, class_result, itemLevel_result, server_result);
            adapter.addItem(mListItem);

            Log.d("loggg", img_result + name_result + charLevel_result + class_result + itemLevel_result + server_result);
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged ();

    }

    //인터넷 연결 확인
    public boolean isNetworkConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        @SuppressLint("MissingPermission") NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        @SuppressLint("MissingPermission") NetworkInfo wimax = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean bwimax = false;
        if (wimax != null)
        {
            bwimax = wimax.isConnected();
        }
        if (mobile != null)
        {
            if (mobile.isConnected() || wifi.isConnected() || bwimax)
            {
                return true;
            }
        }
        else
        {
            if (wifi.isConnected() || bwimax)
            {
                return true;
            }
        }
        return false;
    }
}