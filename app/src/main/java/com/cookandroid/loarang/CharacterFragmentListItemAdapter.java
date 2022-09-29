package com.cookandroid.loarang;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharacterFragmentListItemAdapter extends BaseAdapter {
    ArrayList<CharacterFragmentListItem> items = new ArrayList<CharacterFragmentListItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        CharacterFragmentListItem characterFragmentListItem = items.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_character_listview_item, viewGroup, false);
        }

        ImageView character_image = view.findViewById(R.id.character_image);
        TextView character_nickname = view.findViewById(R.id.character_nickname);
        TextView character_level = view.findViewById(R.id.character_level);
        TextView character_itemLevel = view.findViewById(R.id.character_itemLevel);
        TextView character_server = view.findViewById(R.id.character_server);
        TextView character_class = view.findViewById(R.id.character_class);

        Glide.with(context).load(characterFragmentListItem.getCharacter_image())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(character_image);
        Log.d("glidelogimage", characterFragmentListItem.getCharacter_image());
        //character_image.setImageDrawable(characterFragmentListItem.getCharacter_image());
        character_nickname.setText(characterFragmentListItem.getCharacter_nickname());
        character_level.setText(characterFragmentListItem.getCharacter_level());
        character_itemLevel.setText(characterFragmentListItem.getCharacter_itemLevel());
        character_server.setText(characterFragmentListItem.getCharacter_server());
        character_class.setText(characterFragmentListItem.getCharacter_class());
        return view;
    }

    public void addItem(CharacterFragmentListItem item) {
        items.add(item);
    }
}
