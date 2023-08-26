package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.transition.TransitionManager
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentTrimSelectBinding
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.data.model.TrimCategory
import com.youngcha.ohmycarset.data.dto.OptionCategory
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelectAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeExteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeInteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeMainOptionAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeOptionAdapter
import com.youngcha.ohmycarset.ui.listener.CustomScrollChangeListener
import com.youngcha.ohmycarset.util.MILLISECONDS_PER_INCH
import com.youngcha.ohmycarset.util.decorator.GridItemDecoration
import com.youngcha.ohmycarset.util.decorator.LinearItemDecoration
import com.youngcha.ohmycarset.util.decorator.TopSpacingItemDecoration
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel
import kotlinx.coroutines.launch


class TrimSelectFragment : Fragment() {

    // Binding
    private var _binding: FragmentTrimSelectBinding? = null
    private val binding get() = _binding!!

    private val trimSelectViewModel: TrimSelectViewModel by viewModels()

    private lateinit var trimSelectAdapter: TrimSelectAdapter
    private lateinit var trimSelfModeMainOptionAdapter: TrimSelfModeMainOptionAdapter
    private lateinit var trimSelfModeExteriorColorAdapter: TrimSelfModeExteriorColorAdapter
    private lateinit var trimSelfModeInteriorColorAdapter: TrimSelfModeInteriorColorAdapter
    private lateinit var trimSelfModeOptionAdapter: TrimSelfModeOptionAdapter

    private var trimID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrimSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trimSelectViewModel.viewModelScope.launch {
            binding.layoutGuideMode.guide
        }
        initViews()
        setupRecyclerView()
        observeViewModel()
        clickListener()
    }

    private fun initViews() {
        binding.layoutGuideMode.clRootView.visibility = View.VISIBLE
        binding.layoutSelfMode.clRootView.visibility = View.GONE
        binding.nsvSelfTrimScroll.setOnScrollChangeListener(
            CustomScrollChangeListener(binding.ivDropDownTrim, binding.tvDetailInfoTxt)
        )
        setupTabs()
    }

    private fun setupRecyclerView() {
        trimSelectAdapter = TrimSelectAdapter(trimSelectViewModel)
        binding.rvTrimSelect.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@TrimSelectFragment.trimSelectAdapter
        }

        //Main Option RecyclerView
        trimSelfModeMainOptionAdapter = TrimSelfModeMainOptionAdapter()
        binding.layoutSelfMode.rvMainOption.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.layoutSelfMode.rvMainOption.addItemDecoration(LinearItemDecoration(24))
        binding.layoutSelfMode.rvMainOption.adapter = trimSelfModeMainOptionAdapter

        // Exterior Color RecyclerView
        trimSelfModeExteriorColorAdapter = TrimSelfModeExteriorColorAdapter()
        binding.layoutSelfMode.rvExteriorColor.layoutManager = GridLayoutManager(context, 2)
        binding.layoutSelfMode.rvExteriorColor.addItemDecoration(GridItemDecoration(2, 24, false))
        binding.layoutSelfMode.rvExteriorColor.adapter = trimSelfModeExteriorColorAdapter

        // Interior Color RecyclerView
        trimSelfModeInteriorColorAdapter = TrimSelfModeInteriorColorAdapter()
        binding.layoutSelfMode.rvInteriorColor.layoutManager = LinearLayoutManager(context)
        binding.layoutSelfMode.rvInteriorColor.addItemDecoration(TopSpacingItemDecoration(24))
        binding.layoutSelfMode.rvInteriorColor.adapter = trimSelfModeInteriorColorAdapter

        // Option RecyclerView
        trimSelfModeOptionAdapter = TrimSelfModeOptionAdapter()
        binding.layoutSelfMode.rvOption.layoutManager = LinearLayoutManager(context)
        binding.layoutSelfMode.rvOption.addItemDecoration(TopSpacingItemDecoration(12))
        binding.layoutSelfMode.rvOption.itemAnimator?.changeDuration = 300
        binding.layoutSelfMode.rvOption.adapter = trimSelfModeOptionAdapter

    }

    private fun observeViewModel() {
        trimSelectViewModel.trimCategoryState.observe(viewLifecycleOwner) { trimState ->
            updateRecyclerView(trimState.trimCategories)
            if (!trimState.isFirstLoad) {
                handleTrimTypeChange(trimState.currTrimCategory.type)
            }
        }

        trimSelectViewModel.clickedPosition.observe(viewLifecycleOwner) { position ->
            position?.let { scrollToPosition(it) }
        }

        trimSelectViewModel.model.observe(viewLifecycleOwner) { model ->
            binding.layoutGuideMode.model = model
        }

        trimSelectViewModel.guide.observe(viewLifecycleOwner) { guide ->
            binding.layoutGuideMode.guide = guide
        }

        trimSelectViewModel.trim.observe(viewLifecycleOwner) { trim ->
            binding.layoutSelfMode.trim = trim
            trimID = trim.id
            resetPlusButton()
            trimSelfModeMainOptionAdapter.updateTrimSelfModeMainOption(trim.mainOptions)
            trimSelfModeExteriorColorAdapter.updateTrimSelfModeExteriorColor(trim.exteriorColors)
            trimSelfModeInteriorColorAdapter.updateTrimSelfModeInteriorColor(trim.interiorColors)
        }

        trimSelectViewModel.trimDefaultOption.observe(viewLifecycleOwner) { defaultOption ->
            if (defaultOption != null) {
                trimSelfModeOptionAdapter.updateTrimSelfModeOptions(defaultOption)
            }
        }

        trimSelectViewModel.displayItemCount.observe(viewLifecycleOwner) { count ->
            trimSelfModeOptionAdapter.setDisplayItemCount(count)
        }

        trimSelectViewModel.filteredOptions.observe(viewLifecycleOwner) { options ->
            binding.layoutSelfMode.rvOption.layoutManager?.scrollToPosition(0)
            if (options != null) {
                trimSelfModeOptionAdapter.updateTrimSelfModeOptions(options)
            }
        }
    }

    private fun handleTrimTypeChange(type: TrimType) {
        when (type) {
            TrimType.SELF -> {
                binding.layoutSelfMode.clRootView.visibility = View.VISIBLE
                binding.layoutGuideMode.clRootView.visibility = View.GONE
            }

            TrimType.GUIDE -> {
                binding.layoutGuideMode.clRootView.visibility = View.VISIBLE
                binding.layoutSelfMode.clRootView.visibility = View.GONE
            }
        }
    }

    private fun clickListener() {
        binding.layoutSelfMode.vPlusInfo.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.layoutSelfMode.rvOption)

            binding.layoutSelfMode.vPlusInfo.visibility = View.GONE
            binding.layoutSelfMode.tvPlusInfoTxt.visibility = View.GONE
            binding.layoutSelfMode.ivPlusInfoImg.visibility = View.GONE

            trimSelectViewModel.showAllItems()
        }

        binding.btnTrimSelected.setOnClickListener {
            val bundle: Bundle?

            when (trimSelectViewModel.trimCategoryState.value!!.currTrimCategory.type) {
                TrimType.GUIDE -> {
                    findNavController().navigate(R.id.action_trimSelectFragment_to_estimateReadyFragment)
                }

                TrimType.SELF -> {
                    bundle = Bundle().apply {
                        putString("mode", "SelfMode")
                        putString("startPoint", "start")
                    }
                    findNavController().navigate(
                        R.id.action_trimSelectFragment_to_makeCarModeFragment,
                        bundle
                    )
                }
            }
        }
    }

    private fun setupTabs() {
        val tabNames = mutableListOf(
            OptionCategory.Data.Category(0, "전체"),
            OptionCategory.Data.Category(1, "성능"),
            OptionCategory.Data.Category(12, "지능형 안전기술"),
            OptionCategory.Data.Category(13, "안전"),
            OptionCategory.Data.Category(14, "외관"),
            OptionCategory.Data.Category(15, "내관"),
            OptionCategory.Data.Category(16, "시트"),
            OptionCategory.Data.Category(17, "편의"),
            OptionCategory.Data.Category(18, "멀티미디어")
        )
        for (index in tabNames.indices) {
            val tab = binding.layoutSelfMode.tlOption.newTab()
            val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
            val tvTabName = customView.findViewById<TextView>(R.id.tv_tab_name)
            tvTabName.text = tabNames[index].name
            tab.customView = customView
            tab.view.tag = tabNames[index].id
            binding.layoutSelfMode.tlOption.addTab(tab)
        }

        binding.layoutSelfMode.tlOption.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val categoryId = tab.view.tag as Int
                trimSelectViewModel.filterOptionsByTabName(trimID, categoryId)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun resetPlusButton() {
        binding.layoutSelfMode.tlOption.getTabAt(0)?.select()
        binding.layoutSelfMode.vPlusInfo.visibility = View.VISIBLE
        binding.layoutSelfMode.tvPlusInfoTxt.visibility = View.VISIBLE
        binding.layoutSelfMode.ivPlusInfoImg.visibility = View.VISIBLE
        trimSelectViewModel.showFiveItems()
    }

    private fun updateRecyclerView(trimCategories: List<TrimCategory>) {
        trimSelectAdapter.updateTrims(trimCategories)
    }

    private fun scrollToPosition(position: Int) {
        (binding.rvTrimSelect.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
            val smoothScroller = object : LinearSmoothScroller(requireContext()) {
                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
                }

                override fun getHorizontalSnapPreference(): Int {
                    return SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = position
            layoutManager.startSmoothScroll(smoothScroller)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}