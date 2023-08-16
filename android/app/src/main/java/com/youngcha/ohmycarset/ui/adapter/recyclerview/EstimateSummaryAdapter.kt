package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemEstimateSummaryBinding
import com.youngcha.ohmycarset.model.car.OptionInfo


class EstimateSummaryAdapter() :
    RecyclerView.Adapter<EstimateSummaryAdapter.SummaryViewHolder>() {

    private var summaryOptionList:List<OptionInfo> = emptyList()

    inner class SummaryViewHolder(private val binding: ItemEstimateSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: OptionInfo) {
            binding.tvPrice.text = option.price
            binding.tvType.text = option.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        return SummaryViewHolder(
            ItemEstimateSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = summaryOptionList.size

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(summaryOptionList[position])
    }
}