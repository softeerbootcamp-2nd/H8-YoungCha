package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemEstimateDetailBinding
import com.youngcha.ohmycarset.model.car.OptionInfo


class EstimateDetailAdapter() :
    RecyclerView.Adapter<EstimateDetailAdapter.DetailViewHolder>() {


    //empty list로 시작하면됨. test용
    private var detailOptionList: List<OptionInfo> = listOf(
        OptionInfo(
            "rate", "name", "price", null,
            listOf("1", "2", "3")
        )
    )

    inner class DetailViewHolder(private val binding: ItemEstimateDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: OptionInfo) {

            option.image?.let { binding.ivType.setImageResource(it.source) }
            binding.tvTypePrice.text = option.price

            binding.tvTypeName.text = option.name  // 자세히 보기 다이얼로그

            binding.ivModify    // 해당 탭으로 이동
            binding.tvModify    // 해당 탭으로 이동

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemEstimateDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = detailOptionList.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(detailOptionList[position])
    }
}