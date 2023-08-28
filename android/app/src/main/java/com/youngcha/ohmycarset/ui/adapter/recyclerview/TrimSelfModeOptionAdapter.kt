package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.data.dto.TrimDefaultOption
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeOptionBinding

class TrimSelfModeOptionAdapter: RecyclerView.Adapter<TrimSelfModeOptionAdapter.TrimSelectModeOptionViewHolder>() {

    private var trimSelfModeOptions: List<TrimDefaultOption.Data.Content> = emptyList()
    private var displayItemCount: Int = 5

    fun updateTrimSelfModeOptions(newTrimSelfModeOptions: List<TrimDefaultOption.Data.Content>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = trimSelfModeOptions.size

            override fun getNewListSize() = newTrimSelfModeOptions.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return trimSelfModeOptions[oldItemPosition].categoryId == newTrimSelfModeOptions[newItemPosition].categoryId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return trimSelfModeOptions[oldItemPosition] == newTrimSelfModeOptions[newItemPosition]
            }
        })

        trimSelfModeOptions = newTrimSelfModeOptions
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
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

        fun bind(trimSelfModeOption: TrimDefaultOption.Data.Content) {
            binding.trimOption = trimSelfModeOption
            binding.executePendingBindings()
        }
    }
}