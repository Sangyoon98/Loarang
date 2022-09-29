package com.cookandroid.loarang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CharacterFragment extends Fragment {
    TextView addCharText;
    FloatingActionButton addCharBtn;
    ListView listView;
    CharacterFragmentListItemAdapter adapter;
    CharacterFragmentListItem listItem;
    String nickname = "https://lostark.game.onstove.com/Profile/Character/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        addCharText = (TextView) view.findViewById(R.id.addCharText);
        addCharBtn = view.findViewById(R.id.addCharBtn);
        listView = view.findViewById(R.id.listView);
        adapter = new CharacterFragmentListItemAdapter();

        addCharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = "https://lostark.game.onstove.com/Profile/Character/";
                final EditText dlgEdt = new EditText(getActivity());

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
    Disposable backgroundTask;

    void BackgroundTask(String URLs) {
        //onPreExecute

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
                Elements class_elements = document.select("img[class=profile-character-info__img]");
                class_result = class_elements.attr("alt");
                Elements img_elements = document.select("img[class=profile-character-info__img]");
                img_result = img_elements.attr("src");

                listItem = new CharacterFragmentListItem(img_result, name_result, server_result, charLevel_result, itemLevel_result, class_result);
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
        });
    }
}