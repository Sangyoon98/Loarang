package com.cookandroid.loarang.ui.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookandroid.loarang.R
import com.cookandroid.loarang.databinding.ItemCharacterBinding
import com.cookandroid.loarang.databinding.ItemHomeworkBinding
import com.cookandroid.loarang.room.CharacterEntity

class HomeworkAdapter(
    val context: Context,
    val epona: (nickname: String, count: Int) -> Unit,
    val chaos: (nickname: String, count: Int) -> Unit,
    val gadian: (nickname: String, count: Int) -> Unit,
    val endContent: (nickname: String, count: Int) -> Unit
) : ListAdapter<CharacterEntity, HomeworkAdapter.ViewHolder>(diffCallback) {

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
        val binding = ItemHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHomeworkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterEntity) {
            Glide.with(itemView).load(item.characterImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.characterImage)
            binding.characterNickname.text = item.characterName
            binding.characterItemLevel.text = item.itemLevel

            setBackground(binding.epona1, binding.epona2, binding.epona3, R.color.component_green, item.epona)
            setBackground(binding.chaos, R.color.component_green, item.chaos)
            setBackground(binding.gadian, R.color.component_green, item.gadian)
            setBackground(binding.end1, binding.end2, binding.end3, R.color.component_green, item.endContent)

            binding.containerEpona.setOnClickListener {
                if (item.epona == 3) epona(item.characterName, 0)
                else epona(item.characterName, item.epona + 1)
            }

            binding.containerChaos.setOnClickListener {
                if (item.chaos == 0) chaos(item.characterName, 1)
                else chaos(item.characterName, 0)
            }

            binding.containerGadian.setOnClickListener {
                if (item.gadian == 0) gadian(item.characterName, 1)
                else gadian(item.characterName, 0)
            }

            binding.containerEnd.setOnClickListener {
                if (item.endContent == 3) endContent(item.characterName, 0)
                else endContent(item.characterName, item.endContent + 1)
            }
        }
    }

    fun setBackground(view1: View, view2: View, view3: View, color: Int, count: Int) {
        when (count) {
            0 -> {
                view1.setBackgroundColor(context.getColor(R.color.white))
                view2.setBackgroundColor(context.getColor(R.color.white))
                view3.setBackgroundColor(context.getColor(R.color.white))
            }
            1 -> {
                view1.setBackgroundColor(context.getColor(color))
                view2.setBackgroundColor(context.getColor(R.color.white))
                view3.setBackgroundColor(context.getColor(R.color.white))
            }
            2 -> {
                view1.setBackgroundColor(context.getColor(color))
                view2.setBackgroundColor(context.getColor(color))
                view3.setBackgroundColor(context.getColor(R.color.white))
            }
            3 -> {
                view1.setBackgroundColor(context.getColor(color))
                view2.setBackgroundColor(context.getColor(color))
                view3.setBackgroundColor(context.getColor(color))
            }
        }
    }

    fun setBackground(view: View, color: Int, count: Int) {
        when (count) {
            0 -> view.setBackgroundColor(context.getColor(R.color.white))
            1 -> view.setBackgroundColor(context.getColor(color))
        }
    }
}
