package com.youngcha.ohmycarset.ui.adapter.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemDialogSwipeBinding
import com.youngcha.ohmycarset.data.model.dialog.SwipeDialog

class SwipeAdapter(
    private val swipeDialogData: List<SwipeDialog>,
) : RecyclerView.Adapter<SwipeAdapter.SwipeAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeAdapterViewHolder {
        val binding =
            ItemDialogSwipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwipeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwipeAdapterViewHolder, position: Int) {
        holder.bind(swipeDialogData[position])
    }

    override fun getItemCount() = swipeDialogData.size

    class SwipeAdapterViewHolder(val binding: ItemDialogSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(swipeDialog: SwipeDialog) {
            binding.swipeDialog = swipeDialog
            binding.executePendingBindings()
        }
    }
}