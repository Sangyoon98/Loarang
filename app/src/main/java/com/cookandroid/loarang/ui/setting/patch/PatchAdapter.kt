package com.cookandroid.loarang.ui.setting.patch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cookandroid.loarang.databinding.ItemPatchBinding

class PatchAdapter(
    private val items: ArrayList<PatchModel>,
    var context: Context
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemPatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatchModel) {
            binding.patchName.text = item.name
            binding.patchContext.text = item.context_patch
        }
    }
}
