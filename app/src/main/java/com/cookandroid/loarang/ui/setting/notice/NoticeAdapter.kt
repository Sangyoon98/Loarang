package com.cookandroid.loarang.ui.setting.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cookandroid.loarang.databinding.ItemNoticeBinding

class NoticeAdapter(
    private val items: ArrayList<NoticeModel>,
    val context: Context
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoticeModel) {
            binding.noticeName.text = item.name
            binding.contextNotice.text = item.context_notice
        }
    }
}
