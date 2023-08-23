package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeOptionBinding
import com.youngcha.ohmycarset.data.model.TrimSelfModeOption

class TrimSelfModeOptionAdapter: RecyclerView.Adapter<TrimSelfModeOptionAdapter.TrimSelectModeOptionViewHolder>() {

    private var trimSelfModeOptions: List<TrimSelfModeOption> = emptyList()
    private var displayItemCount: Int = 5

    fun updateTrimSelfModeOptions(newTrimSelfModeOptions: List<TrimSelfModeOption>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = trimSelfModeOptions.size

            override fun getNewListSize() = newTrimSelfModeOptions.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return trimSelfModeOptions[oldItemPosition].type == newTrimSelfModeOptions[newItemPosition].type
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return trimSelfModeOptions[oldItemPosition] == newTrimSelfModeOptions[newItemPosition]
            }
        })

        trimSelfModeOptions = newTrimSelfModeOptions
        diffResult.dispatchUpdatesTo(this)
    }

    fun setDisplayItemCount(count: Int) {
        displayItemCount = count
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimSelectModeOptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelfModeOptionBinding.inflate(inflater, parent, false)
        return TrimSelectModeOptionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrimSelectModeOptionViewHolder,
        position: Int
    ) {
        if (position < trimSelfModeOptions.size)  {
            holder.bind(trimSelfModeOptions[position])
        }
    }

    override fun getItemCount(): Int = if (trimSelfModeOptions.size > displayItemCount) displayItemCount else trimSelfModeOptions.size

    class TrimSelectModeOptionViewHolder(
        private val binding: ItemTrimSelfModeOptionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trimSelfModeOption: TrimSelfModeOption) {
            binding.trimOption = trimSelfModeOption
            binding.executePendingBindings()
        }
    }
}