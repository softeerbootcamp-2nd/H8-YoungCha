package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeOptionBinding
import com.youngcha.ohmycarset.model.TrimSelfModeOption

class TrimSelfModeOptionAdapter: RecyclerView.Adapter<TrimSelfModeOptionAdapter.TrimSelectModeOptionViewHolder>() {

    private var trimSelfModeOptions: List<TrimSelfModeOption> = emptyList()
    private var displayItemCount: Int = 5

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrimSelfModeOptions(trimSelfModeOptions: List<TrimSelfModeOption>) {
        this.trimSelfModeOptions = trimSelfModeOptions
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
        holder.bind(trimSelfModeOptions[position])
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