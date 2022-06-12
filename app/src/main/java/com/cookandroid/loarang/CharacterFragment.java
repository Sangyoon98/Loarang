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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CharacterFragment extends Fragment {
    TextView charName;
    FloatingActionButton addCharBtn;
    String nickname = "https://lostark.game.onstove.com/Profile/Character/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        charName = (TextView) view.findViewById(R.id.charName);
        addCharBtn = view.findViewById(R.id.addCharBtn);

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
                        new CharacterAsyncTask(charName).execute();
                    }
                });
                dlg.show();
            }
        });

        return view;
    }


    class CharacterAsyncTask extends AsyncTask<String, Void, String> {
        TextView textView;

        public CharacterAsyncTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            String name_result = "";
            String server_result = "";
            String charLevel_result = "";
            String itemLevel_result = "";
            String class_result = "";

            try {
                Document document = Jsoup.connect(nickname).get();
                Elements name_elements = document.select("span[class=profile-character-info__name]");
                for (Element element : name_elements) {
                    name_result = name_result + element.text() + "\n";
                }
                Elements server_elements = document.select("span[class=profile-character-info__server]");
                for (Element element : server_elements) {
                    server_result = server_result + element.text() + "\n";
                }
                Elements charLevel_elements = document.select("span[class=profile-character-info__lv]");
                for (Element element : charLevel_elements) {
                    charLevel_result = charLevel_result + element.text() + "\n";
                }
                Elements itemLevel_elements = document.select("div[class=level-info2__expedition]");
                for (Element element : itemLevel_elements) {
                    itemLevel_result = itemLevel_result + element.text() + "\n";
                }
                Elements class_elements = document.select("img[class=profile-character-info__img]");
                class_result = class_elements.attr("alt") + "\n";

                return name_result + server_result + class_result + charLevel_result + itemLevel_result;

                /* 한가지 데이터 부를 경우 백업용
                Document document = Jsoup.connect(nickname).get();
                Elements elements1 = document.select("h5[class=server-name]");
                for (Element element : elements1) {  // for( A : B ) B에서 차례대로 객체를 꺼내서 A에 넣겠다는 뜻
                    result1 = result1 + element.text() + "\n";
                }
                return result1;
                */

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}