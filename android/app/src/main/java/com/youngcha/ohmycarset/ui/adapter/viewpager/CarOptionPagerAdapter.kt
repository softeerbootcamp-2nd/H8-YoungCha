package com.youngcha.ohmycarset.ui.adapter.viewpager

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.ui.customview.HyundaiButtonView
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel

class CarOptionPagerAdapter(private val viewModel: CarCustomizationViewModel) :
    RecyclerView.Adapter<CarOptionPagerAdapter.ViewHolder>() {

    private var options: List<OptionInfo> = emptyList()
    private var optionType: String? = null
    private var subOptionName: String? = null
    private var isDisplayingInPager: Boolean = true
    private var currentType: String? = null

    private val animatedTabs = HashMap<String, Boolean>()
    private var shouldAnimate = false

    /*
    어댑터에서 currentSelectedOptions 사용하여 ViewModel의 상태를 반영하는 것은 문제가 되지 않지만?, 그 변수의 값을 어댑터 내에서 변경하거나 수정하는 행위는 MVVM 패턴에서 어긋남
     */
    private var currentSelectedOptions = listOf<OptionInfo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setOptions(newOptions: List<OptionInfo>, type: String, subOption: String?, isDisplayingInPager: Boolean, currentType: String?) {
        this.options = newOptions
        this.optionType = type
        this.subOptionName = subOption
        this.isDisplayingInPager = isDisplayingInPager
        this.currentType = currentType
        currentSelectedOptions = viewModel.isSelectedOptions(subOption!!) ?: listOf()

        if (animatedTabs[subOption]?.not() == true || animatedTabs[subOption] == null) {
            shouldAnimate = true
            animatedTabs[subOption] = true
        } else {
            shouldAnimate = false
        }

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearOptions() {
        options = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val hyundaiButtonView = HyundaiButtonView(parent.context)
        if (viewType == 1) {
            hyundaiButtonView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }  else if (viewType == 0) {
            val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            val marginBottom = (12 * parent.context.resources.displayMetrics.density).toInt()  // convert 12dp to pixels
            layoutParams.setMargins(0, 0, 0, marginBottom)
            hyundaiButtonView.layoutParams = layoutParams
        }

        return ViewHolder(hyundaiButtonView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isDisplayingInPager) {
            1
        } else {
            0
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position], isDisplayingInPager, currentType)
    }

    override fun getItemCount(): Int = options.size

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(private val hyundaiButtonView: HyundaiButtonView) :
        RecyclerView.ViewHolder(hyundaiButtonView) {
        init {
            hyundaiButtonView.setOnClickListener {
                if (currentType == "GuideMode") {
                    hyundaiButtonView.animateBorder().withEndAction {
                        handleOptionUpdates()
                    }
                } else {
                    handleOptionUpdates()
                }
            }
        }

        private fun handleOptionUpdates() {
            try {
                // 아이템의 위치를 확인하고, 리스트의 범위를 벗어나는지 확인
                if (adapterPosition in 0 until options.size) {
                    val clickedOption = options[adapterPosition]

                    if (optionType == OPTION_SELECTION) {
                        handleOptionSelection(clickedOption)
                    } else {
                        handleSingleOptionSelection(clickedOption)
                    }

                    currentSelectedOptions = viewModel.isSelectedOptions(subOptionName!!) ?: listOf()
                    notifyDataSetChanged()
                } else {
                    Log.e("로그", "Invalid adapter position: $adapterPosition")
                }
            } catch (e: IndexOutOfBoundsException) {
                Log.e("로그", "Index out of bounds: $adapterPosition", e)
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

        fun bind(option: OptionInfo, isViewPager: Boolean, currentType: String?) {
            if (shouldAnimate  && currentType == "GuideMode") {
                hyundaiButtonView.animateBorder()
                shouldAnimate = false // 애니메이션이 한 번 실행된 후 플래그를 초기화
            }
            hyundaiButtonView.setOptionInfo(option)
            hyundaiButtonView.setIsViewPager(isViewPager)
            hyundaiButtonView.setCurrentType(currentType!!)
            hyundaiButtonView.setIsVisible(when {
                optionType == OPTION_SELECTION && currentSelectedOptions.contains(option) -> 1
                optionType != OPTION_SELECTION && (currentSelectedOptions.isEmpty() && adapterPosition == 0 || currentSelectedOptions.contains(
                    option
                )) -> 1

                else -> 0
            })
        }
    }
}