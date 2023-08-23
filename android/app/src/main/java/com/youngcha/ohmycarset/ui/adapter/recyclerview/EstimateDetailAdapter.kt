package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.ItemEstimateDetailBinding
import com.youngcha.ohmycarset.data.model.car.OptionInfo

class EstimateDetailAdapter(private val onOptionKeyClicked: (String) -> Unit) :
    RecyclerView.Adapter<EstimateDetailAdapter.EstimateDetailViewHolder>() {
    private var optionInfoList: List<Pair<String, OptionInfo>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstimateDetailViewHolder {
        val binding: ItemEstimateDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_estimate_detail,
            parent,
            false
        )
        return EstimateDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstimateDetailViewHolder, position: Int) {
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

    inner class EstimateDetailViewHolder(private val binding: ItemEstimateDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.tvModify.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val (optionKey, _) = optionInfoList[position] // 첫 번째 요소는 키 값입니다.
                    onOptionKeyClicked(optionKey)
                }
            }
        }
        fun bind(optionName: String, option: OptionInfo) {
            binding.tvDetailName.text = optionName
            binding.optionInfo = option

            binding.tvTypeName.text = option.name  // 자세히 보기 다이얼로그 연결
            binding.ivModify    // 해당 탭으로 이동
            binding.tvModify    // 해당 탭으로 이동
        }
    }
}
