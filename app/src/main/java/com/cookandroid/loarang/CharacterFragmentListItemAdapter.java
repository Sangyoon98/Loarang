package com.cookandroid.loarang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterFragmentListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CharacterFragmentListItem> items = new ArrayList<CharacterFragmentListItem>();

    public interface OnItemLongClickEventListener {
        void onItemLongClick(int position);
    }

    private int mPosition = RecyclerView.NO_POSITION;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_character_listview_item, parent, false);
        OnItemLongClickEventListener listener = new OnItemLongClickEventListener() {
            @Override
            public void onItemLongClick(int position) {
                mPosition = position;
            }
        };
        return new CharacterFragmentListItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CharacterFragmentListItemViewHolder)holder).onBind(items.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Context context = view.getContext();
                if (pos != RecyclerView.NO_POSITION) {
                    Intent characterFragmentDetail = new Intent(context, CharacterFragmentDetail.class);
                    characterFragmentDetail.putExtra("nickname", items.get(pos).getCharacter_nickname());
                    context.startActivity(characterFragmentDetail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getPosition() {
        return mPosition;
    }

    void addItem(CharacterFragmentListItem item) {
        items.add(item);
    }
}
