package com.cookandroid.loarang.ui.character

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookandroid.loarang.R
import com.cookandroid.loarang.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val items: ArrayList<CharacterFragmentListItem>,
    val context: Context
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener { view ->
            val pos = holder.getAdapterPosition()
            val context = view.context
            if (pos != RecyclerView.NO_POSITION) {
                val characterFragmentDetail = Intent(context, CharacterFragmentDetail::class.java)
                characterFragmentDetail.putExtra("nickname", items[pos].characterNickname)
                context.startActivity(characterFragmentDetail)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterFragmentListItem) {
            Glide.with(itemView).load(item.characterImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.characterImage)
            binding.characterNickname.text = item.characterNickname
            binding.characterLevel.text = item.characterLevel
            binding.characterClass.text = item.characterClass
            binding.characterItemLevel.text = item.characterItemLevel
            binding.characterServer.text = item.characterServer
        }
    }
}
