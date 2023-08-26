package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeMainOptionBinding
import com.youngcha.ohmycarset.data.dto.TrimMainData

class TrimSelfModeMainOptionAdapter :
    RecyclerView.Adapter<TrimSelfModeMainOptionAdapter.TrimSelfModeMainOptionViewHolder>() {

    private var trimSelfModeInteriorColor: List<TrimMainData.Data.Trim.MainOption> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrimSelfModeMainOption(trimSelfModeInteriorColor: List<TrimMainData.Data.Trim.MainOption>) {
        this.trimSelfModeInteriorColor = trimSelfModeInteriorColor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimSelfModeMainOptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelfModeMainOptionBinding.inflate(inflater, parent, false)
        return TrimSelfModeMainOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimSelfModeMainOptionViewHolder, position: Int) {
        holder.bind(trimSelfModeInteriorColor[position])
    }

    override fun getItemCount(): Int = trimSelfModeInteriorColor.size

    class TrimSelfModeMainOptionViewHolder(private val binding: ItemTrimSelfModeMainOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trimSelfModeMainOption: TrimMainData.Data.Trim.MainOption) {
            binding.mainOption = trimSelfModeMainOption
            binding.executePendingBindings()
        }
    }

}