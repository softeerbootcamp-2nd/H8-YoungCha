package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.data.dto.TrimMainData
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeExteiorColorBinding

class TrimSelfModeExteriorColorAdapter :
    RecyclerView.Adapter<TrimSelfModeExteriorColorAdapter.TrimSelfModeExteriorColorViewHolder>() {

    private var trimSelfModeExteriorColor: List<TrimMainData.Data.Trim.ExteriorColor> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrimSelfModeExteriorColor(trimSelfModeExteriorColor: List<TrimMainData.Data.Trim.ExteriorColor>) {
        this.trimSelfModeExteriorColor = trimSelfModeExteriorColor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimSelfModeExteriorColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelfModeExteiorColorBinding.inflate(inflater, parent, false)
        return TrimSelfModeExteriorColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimSelfModeExteriorColorViewHolder, position: Int) {
        holder.bind(trimSelfModeExteriorColor[position])
    }

    override fun getItemCount(): Int = trimSelfModeExteriorColor.size

    class TrimSelfModeExteriorColorViewHolder(
        private val binding: ItemTrimSelfModeExteiorColorBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trimSelfModeExteriorColor: TrimMainData.Data.Trim.ExteriorColor) {
            binding.exteriorColor = trimSelfModeExteriorColor
            binding.rank = adapterPosition
            binding.executePendingBindings()
        }
    }
}