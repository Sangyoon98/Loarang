package com.cookandroid.loarang.ui.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cookandroid.loarang.R
import com.cookandroid.loarang.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val items: ArrayList<ScheduleModel>,
    var context: Context
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ScheduleModel) {
            Glide.with(itemView).load(item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.scheduleContentImage)
            binding.calenderLocation.text = item.location
            binding.calenderName.text = item.name
            binding.calenderTime.text = item.time
        }
    }
}
