package com.youngcha.ohmycarset.ui.adapter.viewpager

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.LayoutHyundaiButtonBinding
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel

class CarOptionPagerAdapter(private val viewModel: CarCustomizationViewModel) :
    RecyclerView.Adapter<CarOptionPagerAdapter.ViewHolder>() {

    private var options: List<OptionInfo> = emptyList()
    private var optionType: String? = null
    private var subOptionName: String? = null
    var isDisplayingInPager: Boolean = true

    /*
    어댑터에서 currentSelectedOptions 사용하여 ViewModel의 상태를 반영하는 것은 문제가 되지 않지만?, 그 변수의 값을 어댑터 내에서 변경하거나 수정하는 행위는 MVVM 패턴에서 어긋남
     */
    private var currentSelectedOptions = listOf<OptionInfo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setOptions(newOptions: List<OptionInfo>, type: String, subOption: String?) {
        this.options = newOptions
        this.optionType = type
        this.subOptionName = subOption
        currentSelectedOptions = viewModel.isSelectedOptions(subOption!!) ?: listOf()

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearOptions() {
        options = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutHyundaiButtonBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_hyundai_button, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position], isDisplayingInPager)
    }

    override fun getItemCount(): Int = options.size

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(private val binding: LayoutHyundaiButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val clickedOption = options[adapterPosition]
                if (optionType == OPTION_SELECTION) {
                    handleOptionSelection(clickedOption)
                } else {
                    handleSingleOptionSelection(clickedOption)
                }

                currentSelectedOptions = viewModel.isSelectedOptions(subOptionName!!) ?: listOf()
                notifyDataSetChanged()
            }
        }

        private fun handleOptionSelection(clickedOption: OptionInfo) {
            val isSelected = currentSelectedOptions.contains(clickedOption)
            if (isSelected) {
                subOptionName?.let { viewModel.removeCarComponents(it, clickedOption) }
            } else {
                subOptionName?.let {
                    viewModel.addCarComponents(viewModel.currentComponentName.value!!, clickedOption, it)
                } ?: viewModel.addCarComponents(viewModel.currentComponentName.value!!, clickedOption)
            }
        }

        private fun handleSingleOptionSelection(clickedOption: OptionInfo) {
            viewModel.addCarComponents(viewModel.currentComponentName.value!!, clickedOption, subOptionName)
        }

        fun bind(option: OptionInfo, isViewPager: Boolean) {
            binding.optionInfo = option
            binding.isVisible = when {
                optionType == OPTION_SELECTION && currentSelectedOptions.contains(option) -> 1
                optionType != OPTION_SELECTION && (currentSelectedOptions.isEmpty() && adapterPosition == 0 || currentSelectedOptions.contains(
                    option
                )) -> 1

                else -> 0
            }
            binding.isViewPager = isViewPager
            binding.executePendingBindings()
        }
    }
}