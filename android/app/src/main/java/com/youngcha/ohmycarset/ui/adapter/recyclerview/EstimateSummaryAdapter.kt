package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.ItemEstimateSummaryBinding
import com.youngcha.ohmycarset.data.model.car.OptionInfo

class EstimateSummaryAdapter
    : RecyclerView.Adapter<EstimateSummaryAdapter.EstimateSummaryViewHolder>() {
    private var optionInfoList: List<Pair<String, OptionInfo>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstimateSummaryViewHolder {
        val binding: ItemEstimateSummaryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_estimate_summary,
            parent,
            false
        )
        return EstimateSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstimateSummaryViewHolder, position: Int) {
        val (optionName, option) = optionInfoList[position]
        holder.bind(optionName, option)
    }

    override fun getItemCount(): Int = optionInfoList.size

    fun updateOptionInfo(optionInfoMap: Map<String, List<OptionInfo>>) {
        this.optionInfoList = optionInfoMap.flatMap { entry ->
            entry.value.map { option -> Pair(entry.key, option) }
        }
        notifyDataSetChanged()
    }

    inner class EstimateSummaryViewHolder(private val binding: ItemEstimateSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(optionName: String, option: OptionInfo) {
            val previousOptionName = if (adapterPosition > 0) {
                optionInfoList[adapterPosition - 1].first
            } else {
                null
            }

            if (optionName == previousOptionName) {
                binding.componentName = ""
            } else {
                binding.componentName = optionName
            }

            binding.optionInfo = option
            binding.executePendingBindings()
        }
    }
}