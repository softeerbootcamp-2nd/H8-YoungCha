package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.data.dto.TrimMainData
import com.youngcha.ohmycarset.databinding.ItemTrimSelfModeMainOptionBinding

class TrimSelfModeMainFeatureAdapter :
    RecyclerView.Adapter<TrimSelfModeMainFeatureAdapter.TrimSelfModeMainOptionViewHolder>() {

    private var trimSelfModeMainFeature: List<TrimMainData.Data.Trim.MainOption> = listOf(
        TrimMainData.Data.Trim.MainOption("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/%ED%95%B5%EC%8B%AC+%EC%98%B5%EC%85%98/Frame+48096610.png","나의 상황과 유형 선택"),
        TrimMainData.Data.Trim.MainOption("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/%ED%95%B5%EC%8B%AC+%EC%98%B5%EC%85%98/Frame+48096611.png","유형 분석 후 나에게 맞게 추천"),
        TrimMainData.Data.Trim.MainOption("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/%ED%95%B5%EC%8B%AC+%EC%98%B5%EC%85%98/Frame+48096612.png","내 취향에 맞게 수정 및 완성")
    )
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimSelfModeMainOptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelfModeMainOptionBinding.inflate(inflater, parent, false)
        return TrimSelfModeMainOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimSelfModeMainOptionViewHolder, position: Int) {
        holder.bind(trimSelfModeMainFeature[position])
    }

    override fun getItemCount(): Int = trimSelfModeMainFeature.size

    class TrimSelfModeMainOptionViewHolder(private val binding: ItemTrimSelfModeMainOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trimSelfModeMainFeature: TrimMainData.Data.Trim.MainOption) {
            binding.mainOption = trimSelfModeMainFeature
            binding.executePendingBindings()
        }
    }

}