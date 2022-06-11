package com.cookandroid.loarang;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CharacterFragment extends Fragment {
    TextView charName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);
        charName = (TextView) view.findViewById(R.id.charName);
        new CharacterAsyncTask(charName).execute();

        return view;
    }

    class CharacterAsyncTask extends AsyncTask<String, Void, String> {
        TextView textView;

        public CharacterAsyncTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            try {
                Document document = Jsoup.connect("https://lostark.game.onstove.com/Profile/Character/0iloll0").get();
                Elements elements = document.select("span[class=profile-character-info__name]");
                for (Element element : elements) {  // for( A : B ) B에서 차례대로 객체를 꺼내서 A에 넣겠다는 뜻
                    result = result + element.text() + "\n";
                }
                return result;

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