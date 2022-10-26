package com.cookandroid.loarang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CharacterFragmentListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CharacterFragmentListItem> items = new ArrayList<CharacterFragmentListItem>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_character_listview_item, parent, false);
        return new CharacterFragmentListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CharacterFragmentListItemViewHolder)holder).onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(CharacterFragmentListItem item) {
        items.add(item);
    }
}
