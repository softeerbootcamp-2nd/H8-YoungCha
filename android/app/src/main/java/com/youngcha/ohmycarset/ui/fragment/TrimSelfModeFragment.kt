package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentTrimSelfModeBinding
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeExteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeInteriorColorAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeOptionAdapter
import com.youngcha.ohmycarset.util.decorator.GridSpacingItemDecoration
import com.youngcha.ohmycarset.util.decorator.TopSpacingItemDecoration
import com.youngcha.ohmycarset.viewmodel.TrimSelfModeViewModel

class TrimSelfModeFragment : Fragment() {

    // Args
    private val args: TrimSelfModeFragmentArgs by navArgs()

    // ViewModel
    private val viewModel: TrimSelfModeViewModel by viewModels()

    // Binding
    private var _binding: FragmentTrimSelfModeBinding? = null
    private val binding get() = _binding!!

    // Adapters
    private lateinit var trimSelfModeExteriorColorAdapter: TrimSelfModeExteriorColorAdapter
    private lateinit var trimSelfModeInteriorColorAdapter: TrimSelfModeInteriorColorAdapter
    private lateinit var trimSelfModeOptionAdapter: TrimSelfModeOptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrimSelfModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // val selectedTrimName = args.selectedTrimName

        setupTabs()
        clickListener()
        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.trimSelfModeData.observe(viewLifecycleOwner) { trim ->
            binding.trim = trim
            trimSelfModeExteriorColorAdapter.updateTrimSelfModeExteriorColor(trim.exteriorColor)
            trimSelfModeInteriorColorAdapter.updateTrimSelfModeInteriorColor(trim.interiorColor)
            trimSelfModeOptionAdapter.updateTrimSelfModeOptions(trim.options)
        }

        viewModel.displayItemCount.observe(viewLifecycleOwner) { count ->
            trimSelfModeOptionAdapter.setDisplayItemCount(count)
        }

        viewModel.filteredOptions.observe(viewLifecycleOwner) { options ->
            binding.rvOption.layoutManager?.scrollToPosition(0)
            trimSelfModeOptionAdapter.updateTrimSelfModeOptions(options)
        }
    }

    private fun clickListener() {
        binding.vPlusInfo.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.rvOption)

            binding.vPlusInfo.visibility = View.GONE
            binding.tvPlusInfoTxt.visibility = View.GONE
            binding.ivPlusInfoImg.visibility = View.GONE

            viewModel.showAllItems()
        }
    }

    private fun setupRecyclerView() {
        // Exterior Color RecyclerView
        trimSelfModeExteriorColorAdapter = TrimSelfModeExteriorColorAdapter()
        binding.rvExteriorColor.layoutManager = GridLayoutManager(context, 2)
        binding.rvExteriorColor.addItemDecoration(GridSpacingItemDecoration(2, 6, 8))
        binding.rvExteriorColor.adapter = trimSelfModeExteriorColorAdapter

        // Interior Color RecyclerView
        trimSelfModeInteriorColorAdapter = TrimSelfModeInteriorColorAdapter()
        binding.rvInteriorColor.layoutManager = LinearLayoutManager(context)
        binding.rvInteriorColor.addItemDecoration(TopSpacingItemDecoration(24))
        binding.rvInteriorColor.adapter = trimSelfModeInteriorColorAdapter

        // Option RecyclerView
        trimSelfModeOptionAdapter = TrimSelfModeOptionAdapter()
        binding.rvOption.layoutManager = LinearLayoutManager(context)
        binding.rvOption.addItemDecoration(TopSpacingItemDecoration(12))
        binding.rvOption.itemAnimator?.changeDuration = 300
        binding.rvOption.adapter = trimSelfModeOptionAdapter

    }

    private fun setupTabs() {
        val tabNames = listOf("전체", "성능", "지능형 안전기술", "안전", "외관", "내장", "시트", "편의", "멀티미디어")

        for (name in tabNames) {
            val tab = binding.tlOption.newTab()
            val customView = layoutInflater.inflate(R.layout.view_custom_tab, null)
            val tvTabName = customView.findViewById<TextView>(R.id.tv_tab_name)
            tvTabName.text = name
            tab.customView = customView
            binding.tlOption.addTab(tab)
        }

        binding.tlOption.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val tvTabName = customView?.findViewById<TextView>(R.id.tv_tab_name)
                val tabName = tvTabName?.text.toString() ?: ""

                viewModel.filterOptionsByTabName(tabName)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}