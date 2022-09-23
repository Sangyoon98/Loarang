package com.cookandroid.loarang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CalenderFragment extends Fragment {
    ArrayList actors;
    ListView customListView;
    private static CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        actors = new ArrayList<>();
        actors.add(new Actor("카오스게이트","https://loawa.com/assets/images/island/chaos_01.png","매 시간 정각"));
        actors.add(new Actor("필드 보스","https://loawa.com/assets/images/island/boss_13.png","매 시간 정각"));
        actors.add(new Actor("모험 섬","https://cdn-lostark.game.onstove.com/uploadfiles/banner/ef5b1d5004ad4094893234a752ac9e70.jpg","매 시간 정각"));
        actors.add(new Actor("점령전","https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/information/contents/tower/5.jpg?ca30c98e745f4558d976","매 시간 정각"));
        actors.add(new Actor("떠돌이 상인","https://cdn-lostark.game.onstove.com/uploadfiles/notice/a42b213f4a1e49df80fc3c4230836b70.png","매 시간 30분"));
        actors.add(new Actor("유령선","https://cdn-lostark.game.onstove.com/uploadfiles/notice/1114dff33d3c4203878065bd6de1bbfd.jpg","매 시간 정각"));
        actors.add(new Actor("대륙 협동","https://cdn-lostark.game.onstove.com/uploadfiles/notice/0151ff6fa3f7487e911c97d3ae65cdb2.png","매 시간 30분"));
        actors.add(new Actor("정기 점검","https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/information/contents/tower/5.jpg?ca30c98e745f4558d976","수요일 6시 정각"));
        customListView = (ListView) view.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(),actors);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
            }
        });
        return view;
    }
}

class Actor {
    private String name;
    private String summary;
    private String thumb_url;

    public Actor(String name,String thumb_url,String summary){
        this.name = name;
        this.summary = summary;
        this.thumb_url = thumb_url;
    }
    public String getName(){
        return name;
    }
    public String getSummary(){
        return summary;
    }
    public String getThumb_url(){
        return thumb_url;
    }

}