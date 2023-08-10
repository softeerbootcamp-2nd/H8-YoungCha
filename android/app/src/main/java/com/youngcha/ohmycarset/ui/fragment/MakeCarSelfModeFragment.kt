package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.databinding.FragmentMakeCarSelfModeBinding
import com.youngcha.ohmycarset.enums.AdditionalTab
import com.youngcha.ohmycarset.enums.CarOption
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel

class MakeCarSelfModeFragment : Fragment() {
    private var _binding: FragmentMakeCarSelfModeBinding? = null
    private val binding get() = _binding!!

    private val carViewModel: CarCustomizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeCarSelfModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carViewModel.loadCarData("팰리세이드")
        observeViewModel()
        setupTabSelectionListener()
    }

    private fun observeViewModel() {
        carViewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            car.mainOptions[0].keys.forEachIndexed { index, carOption ->
                val tabName = "${String.format("%02d", index + 1)} ${carOption.displayName}"
                binding.makeCarTabLayout.addTab(binding.makeCarTabLayout.newTab().setText(tabName))
            }

            // 추가 탭 항목 생성
            AdditionalTab.values().forEachIndexed { index, additionalTab ->
                val tabIndex = car.mainOptions[0].keys.size + index + 1
                val tabName = "${String.format("%02d", tabIndex)} ${additionalTab.displayName}"
                binding.makeCarTabLayout.addTab(binding.makeCarTabLayout.newTab().setText(tabName))
            }
        }
    }

    private fun getTabName(carOption: CarOption): String {
        val index = carOption.ordinal + 1
        return String.format("%02d %s", index, carOption.displayName)
    }

    private fun setupTabSelectionListener() {
        binding.makeCarTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabName = tab?.text ?: "Unknown"
                Snackbar.make(binding.root, "Selected tab: $tabName", Snackbar.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}