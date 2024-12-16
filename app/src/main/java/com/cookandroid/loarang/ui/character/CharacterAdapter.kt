package com.cookandroid.loarang.ui.character

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookandroid.loarang.R
import com.cookandroid.loarang.databinding.ItemCharacterBinding
import com.cookandroid.loarang.room.CharacterEntity

class CharacterAdapter(
    //private val items: ArrayList<CharacterEntity>,
    val context: Context
) : ListAdapter<CharacterEntity, CharacterAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem.characterName == newItem.characterName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener { view ->
            val pos = holder.getAdapterPosition()
            val context = view.context
            if (pos != RecyclerView.NO_POSITION) {
                val characterFragmentDetail = Intent(context, CharacterFragmentDetail::class.java)
                characterFragmentDetail.putExtra("nickname", getItem(pos).characterName)
                context.startActivity(characterFragmentDetail)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterEntity) {
            Glide.with(itemView).load(item.characterImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.characterImage)
            binding.characterNickname.text = item.characterName
            binding.characterLevel.text = item.characterLevel
            binding.characterClass.text = item.characterClassName
            binding.characterItemLevel.text = item.itemLevel
            binding.characterServer.text = item.serverName
        }
    }
}
