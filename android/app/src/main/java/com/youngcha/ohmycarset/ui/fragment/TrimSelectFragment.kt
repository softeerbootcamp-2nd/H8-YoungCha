package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentTrimSelectBinding
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.model.TrimCategory
import com.youngcha.ohmycarset.model.dialog.ButtonDialog
import com.youngcha.ohmycarset.model.dialog.ButtonHorizontal
import com.youngcha.ohmycarset.model.dialog.ButtonVertical
import com.youngcha.ohmycarset.model.dialog.TextDialog
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelectAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeExteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeInteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeOptionAdapter
import com.youngcha.ohmycarset.ui.customview.ButtonDialogView
import com.youngcha.ohmycarset.ui.customview.TextDialogView
import com.youngcha.ohmycarset.ui.listener.CustomScrollChangeListener
import com.youngcha.ohmycarset.util.MILLISECONDS_PER_INCH
import com.youngcha.ohmycarset.util.decorator.GridSpacingItemDecoration
import com.youngcha.ohmycarset.util.decorator.TopSpacingItemDecoration
import com.youngcha.ohmycarset.util.fadeIn
import com.youngcha.ohmycarset.util.fadeOut
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel
import com.youngcha.ohmycarset.viewmodel.TrimSelfModeViewModel


class TrimSelectFragment : Fragment() {

    // Binding
    private var _binding: FragmentTrimSelectBinding? = null
    private val binding get() = _binding!!

    private val trimSelectViewModel: TrimSelectViewModel by viewModels()
    private val trimSelfModeViewModel: TrimSelfModeViewModel by viewModels()

    private lateinit var trimSelectAdapter: TrimSelectAdapter
    private lateinit var trimSelfModeExteriorColorAdapter: TrimSelfModeExteriorColorAdapter
    private lateinit var trimSelfModeInteriorColorAdapter: TrimSelfModeInteriorColorAdapter
    private lateinit var trimSelfModeOptionAdapter: TrimSelfModeOptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrimSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupRecyclerView()
        observeViewModel()
        clickListener()
        setupTabs()
    }

    private fun initViews() {
        binding.layoutGuideMode.clRootView.fadeIn()
        binding.layoutSelfMode.clRootView.fadeOut()

        binding.nsvSelfTrimScroll.setOnScrollChangeListener(
            CustomScrollChangeListener(binding.ivDropDownTrim, binding.tvDetailInfoTxt)
        )
    }

    private fun setupRecyclerView() {
        trimSelectAdapter = TrimSelectAdapter(trimSelectViewModel)
        binding.rvTrimSelect.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@TrimSelectFragment.trimSelectAdapter
        }

        // Exterior Color RecyclerView
        trimSelfModeExteriorColorAdapter = TrimSelfModeExteriorColorAdapter()
        binding.layoutSelfMode.rvExteriorColor.layoutManager = GridLayoutManager(context, 2)
        binding.layoutSelfMode.rvExteriorColor.addItemDecoration(GridSpacingItemDecoration(2, 6, 8))
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

        trimSelfModeViewModel.trimSelfModeData.observe(viewLifecycleOwner) { trim ->
            binding.layoutSelfMode.trim = trim
            trimSelfModeExteriorColorAdapter.updateTrimSelfModeExteriorColor(trim.exteriorColor)
            trimSelfModeInteriorColorAdapter.updateTrimSelfModeInteriorColor(trim.interiorColor)
            trimSelfModeOptionAdapter.updateTrimSelfModeOptions(trim.options)
        }

        trimSelfModeViewModel.displayItemCount.observe(viewLifecycleOwner) { count ->
            trimSelfModeOptionAdapter.setDisplayItemCount(count)
        }

        trimSelfModeViewModel.filteredOptions.observe(viewLifecycleOwner) { options ->
            binding.layoutSelfMode.rvOption.layoutManager?.scrollToPosition(0)
            trimSelfModeOptionAdapter.updateTrimSelfModeOptions(options)
        }
    }

    private fun handleTrimTypeChange(type: TrimType) {
        when (type) {
            TrimType.SELF -> {
                binding.layoutSelfMode.clRootView.fadeIn()
                binding.layoutGuideMode.clRootView.fadeOut()
            }

            TrimType.GUIDE -> {
                binding.layoutGuideMode.clRootView.fadeIn()
                binding.layoutSelfMode.clRootView.fadeOut()
            }
        }
    }

    private fun clickListener() {
        binding.layoutSelfMode.vPlusInfo.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.layoutSelfMode.rvOption)

            binding.layoutSelfMode.vPlusInfo.visibility = View.GONE
            binding.layoutSelfMode.tvPlusInfoTxt.visibility = View.GONE
            binding.layoutSelfMode.ivPlusInfoImg.visibility = View.GONE

            trimSelfModeViewModel.showAllItems()
        }

        binding.btnTrimSelected.setOnClickListener {
            var bundle: Bundle?

            when(trimSelectViewModel.trimCategoryState.value!!.currTrimCategory.type) {
                TrimType.GUIDE -> {
                    findNavController().navigate(R.id.action_trimSelectFragment_to_estimateReadyFragment)
                }

                TrimType.SELF -> {
                    bundle = Bundle().apply {
                        putString("mode", "SelfMode")
                        putString("startPoint", "start")
                    }
                    findNavController().navigate(R.id.action_trimSelectFragment_to_makeCarModeFragment, bundle)
                }
            }
        }
    }

    private fun setupTabs() {
        val tabNames = listOf("전체", "성능", "지능형 안전기술", "안전", "외관", "내장", "시트", "편의", "멀티미디어")

        for (name in tabNames) {
            val tab = binding.layoutSelfMode.tlOption.newTab()
            val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
            val tvTabName = customView.findViewById<TextView>(R.id.tv_tab_name)
            tvTabName.text = name
            tab.customView = customView
            binding.layoutSelfMode.tlOption.addTab(tab)
        }

        binding.layoutSelfMode.tlOption.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val tvTabName = customView?.findViewById<TextView>(R.id.tv_tab_name)
                val tabName = tvTabName?.text.toString() ?: ""

                trimSelfModeViewModel.filterOptionsByTabName(tabName)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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