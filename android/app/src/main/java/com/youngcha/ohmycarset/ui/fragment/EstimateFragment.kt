package com.youngcha.ohmycarset.ui.fragment

import androidx.fragment.app.Fragment
import com.youngcha.ohmycarset.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.databinding.FragmentEstimateBinding
import com.youngcha.ohmycarset.ui.adapter.recyclerview.EstimateDetailAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.EstimateSummaryAdapter
import com.youngcha.ohmycarset.util.decorator.LinearItemDecoration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EstimateFragment : Fragment() {
    private var _binding: FragmentEstimateBinding? = null
    private val binding get() = _binding!!

    private lateinit var summaryAdapter: EstimateSummaryAdapter
    private lateinit var detailAdapter: EstimateDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstimateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        particleAnimation()
        toggleButton()
        setupTabs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTabs() {
        val tabNames = listOf("전체", "시스템", "편의", "디자인", "주행")

        for (name in tabNames) {
            val tab = binding.lyDetail.tlOption.newTab()
            val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
            val tvTabName = customView.findViewById<TextView>(R.id.tv_tab_name)
            tvTabName.text = name
            tab.customView = customView
            binding.lyDetail.tlOption.addTab(tab)
        }

        binding.lyDetail.tlOption.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val tvTabName = customView?.findViewById<TextView>(R.id.tv_tab_name)
                val tabName = tvTabName?.text.toString() ?: ""

                //trimSelfModeViewModel.filterOptionsByTabName(tabName)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun toggleButton() {
        binding.btnExterior.setOnClickListener {
            binding.ivEstimateDone.setImageResource(R.drawable.img_trim_leblanc)
            binding.btnExterior.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_hyundai_blue
                )
            )
            binding.btnExterior.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.btnInterior.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cool_grey_001
                )
            )
            binding.btnInterior.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cool_grey_black
                )
            )
        }
        binding.btnInterior.setOnClickListener {
            binding.ivEstimateDone.setImageResource(R.drawable.img_test_make_car_05)
            binding.btnExterior.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cool_grey_001
                )
            )
            binding.btnExterior.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cool_grey_black
                )
            )
            binding.btnInterior.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_hyundai_blue
                )
            )
            binding.btnInterior.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }

    private fun particleAnimation() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)

            val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

            val animationSet = AnimationSet(true)
            animationSet.addAnimation(slideInAnimation)
            animationSet.addAnimation(fadeInAnimation)

            binding.ivParticle.visibility = View.VISIBLE
            binding.ivParticle.startAnimation(animationSet)
        }
    }

    private fun initAdapter() {
        summaryAdapter = EstimateSummaryAdapter()
        detailAdapter = EstimateDetailAdapter()

        binding.lySummary.rvChooserOption.layoutManager = LinearLayoutManager(activity)
        binding.lySummary.rvChooserOption.adapter = summaryAdapter

        binding.lyDetail.rvChooserOption.layoutManager = LinearLayoutManager(activity)
        binding.lyDetail.rvChooserOption.adapter = detailAdapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.lySummary.rvChooserOption.addItemDecoration(LinearItemDecoration(spacingInPixels))
        binding.lyDetail.rvChooserOption.addItemDecoration(LinearItemDecoration(spacingInPixels))
    }
}