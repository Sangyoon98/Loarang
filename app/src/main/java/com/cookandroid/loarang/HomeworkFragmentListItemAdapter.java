package com.cookandroid.loarang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeworkFragmentListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<HomeworkFragmentListItem> items = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_homework_listview_item, parent, false);
        return new HomeworkFragmentListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HomeworkFragmentListItemViewHolder)holder).onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(HomeworkFragmentListItem item) {
        items.add(item);
    }
}
