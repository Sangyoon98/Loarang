package com.cookandroid.loarang;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CharacterFragmentListItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    ImageView character_image;
    TextView character_nickname, character_level, character_class, character_itemLevel, character_server;

    public CharacterFragmentListItemViewHolder(@NonNull View itemView, CharacterFragmentListItemAdapter.OnItemLongClickEventListener itemLongClickEventListener) {
        super(itemView);

        itemView.setOnCreateContextMenuListener(this);
        character_image = itemView.findViewById(R.id.character_image);
        character_nickname = itemView.findViewById(R.id.character_nickname);
        character_level = itemView.findViewById(R.id.character_level);
        character_class = itemView.findViewById(R.id.character_class);
        character_itemLevel = itemView.findViewById(R.id.character_itemLevel);
        character_server = itemView.findViewById(R.id.character_server);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemLongClickEventListener.onItemLongClick(position);
                }
                return false;
            }
        });
    }

    public void onBind(CharacterFragmentListItem characterFragmentListItem) {
        Glide.with(itemView).load(characterFragmentListItem.getCharacter_image())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(character_image);
        character_nickname.setText(characterFragmentListItem.getCharacter_nickname());
        character_level.setText(characterFragmentListItem.getCharacter_level());
        character_class.setText(characterFragmentListItem.getCharacter_class());
        character_itemLevel.setText(characterFragmentListItem.getCharacter_itemLevel());
        character_server.setText(characterFragmentListItem.getCharacter_server());

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        ((Activity) view.getContext()).getMenuInflater().inflate(R.menu.character_item_menu, contextMenu);
    }
}
