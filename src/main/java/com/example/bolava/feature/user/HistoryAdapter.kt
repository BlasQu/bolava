package com.example.bolava.feature.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bolava.databinding.HistoryItemBinding
import com.example.bolava.util.DiffCallback
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class HistoryAdapter @Inject constructor(
): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val data = mutableListOf<String>()

    inner class ViewHolder(val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.binding.textTitle.text = "test"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(newList: List<String>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(data, newList))
        data.apply {
            clear()
            addAll(newList)
        }
        diff.dispatchUpdatesTo(this)
    }
}