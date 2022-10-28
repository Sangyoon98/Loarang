package com.cookandroid.loarang;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CharacterFragmentListItemViewHolder extends RecyclerView.ViewHolder {
    ImageView character_image;
    TextView character_nickname, character_level, character_class, character_itemLevel, character_server;

    public CharacterFragmentListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        character_image = itemView.findViewById(R.id.character_image);
        character_nickname = itemView.findViewById(R.id.character_nickname);
        character_level = itemView.findViewById(R.id.character_level);
        character_class = itemView.findViewById(R.id.character_class);
        character_itemLevel = itemView.findViewById(R.id.character_itemLevel);
        character_server = itemView.findViewById(R.id.character_server);

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
}
