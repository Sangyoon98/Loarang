package com.cookandroid.loarang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalenderFragmentListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CalenderFragmentListItem> items;
    Context context;

    public CalenderFragmentListItemAdapter(ArrayList<CalenderFragmentListItem> arrayList, Context context) {
        this.items = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calender_listview_item, parent, false);
        return new CalenderFragmentListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CalenderFragmentListItemViewHolder)holder).onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(CalenderFragmentListItem data) {
        items.add(data);
    }
}
