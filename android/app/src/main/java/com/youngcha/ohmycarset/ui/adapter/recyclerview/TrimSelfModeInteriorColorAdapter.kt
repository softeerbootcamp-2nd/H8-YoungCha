package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.data.dto.TrimMainData
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeInteriorColorBinding

class TrimSelfModeInteriorColorAdapter :
    RecyclerView.Adapter<TrimSelfModeInteriorColorAdapter.TrimSelfModeInteriorColorViewHolder>() {

    private var trimSelfModeInteriorColor: List<TrimMainData.Data.Trim.InteriorColor> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrimSelfModeInteriorColor(trimSelfModeInteriorColor: List<TrimMainData.Data.Trim.InteriorColor>) {
        this.trimSelfModeInteriorColor = trimSelfModeInteriorColor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimSelfModeInteriorColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelfModeInteriorColorBinding.inflate(inflater, parent, false)
        return TrimSelfModeInteriorColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimSelfModeInteriorColorViewHolder, position: Int) {
        holder.bind(trimSelfModeInteriorColor[position])
    }

    override fun getItemCount(): Int = trimSelfModeInteriorColor.size

    class TrimSelfModeInteriorColorViewHolder(private val binding: ItemTrimSelfModeInteriorColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trimSelfModeInteriorColor: TrimMainData.Data.Trim.InteriorColor) {
            binding.interiorColor = trimSelfModeInteriorColor
            binding.executePendingBindings()
        }
    }

}