package com.example.bolava.feature.user

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bolava.databinding.HistoryItemBinding
import javax.inject.Inject

class HistoryAdapter @Inject constructor(
): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}