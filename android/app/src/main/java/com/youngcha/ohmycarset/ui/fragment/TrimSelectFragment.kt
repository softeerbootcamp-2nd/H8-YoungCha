package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.snackbar.Snackbar
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentTrimSelectBinding
import com.youngcha.ohmycarset.databinding.FragmentTrimSelfModeBinding
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.model.TrimCategory
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelectAdapter
import com.youngcha.ohmycarset.ui.listener.CustomScrollChangeListener
import com.youngcha.ohmycarset.util.MILLISECONDS_PER_INCH
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel


class TrimSelectFragment : Fragment() {

    // Binding
    private var _binding: FragmentTrimSelectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrimSelectViewModel by viewModels()
    private lateinit var adapter: TrimSelectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrimSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeViewModel()

    }

    private fun init() {
        adapter = TrimSelectAdapter(viewModel)

        binding.rvTrimSelect.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@TrimSelectFragment.adapter
        }

        binding.nsvSelfTrimScroll.setOnScrollChangeListener(
            CustomScrollChangeListener(binding.ivDropDownTrim, binding.tvDetailInfoTxt)
        )

        binding.layoutGuideMode.clRootView.animate()
            .alpha(1f) // 투명도 1로 변경 (완전 불투명)
            .setDuration(500) // 애니메이션 지속 시간 설정 (500ms)
            .start()

        binding.layoutSelfMode.clRootView.animate()
            .alpha(0f) // 투명도 0으로 변경 (완전 투명)
            .setDuration(500)
            .start()
    }

    private fun observeViewModel() {
        viewModel.trimCategoryState.observe(viewLifecycleOwner) { trimState ->
            updateRecyclerView(trimState.trimCategories)

            if (!trimState.isFirstLoad) {
                when (trimState.currTrimCategory.type) {
                    TrimType.SELF -> {
                        binding.layoutSelfMode.clRootView.animate()
                            .alpha(1f) // 투명도 1로 변경 (완전 불투명)
                            .setDuration(500) // 애니메이션 지속 시간 설정 (500ms)
                            .start()

                        binding.layoutGuideMode.clRootView.animate()
                            .alpha(0f) // 투명도 0으로 변경 (완전 투명)
                            .setDuration(500)
                            .start()
                    }

                    TrimType.GUIDE -> {
                        binding.layoutGuideMode.clRootView.animate()
                            .alpha(1f) // 투명도 1로 변경 (완전 불투명)
                            .setDuration(500) // 애니메이션 지속 시간 설정 (500ms)
                            .start()

                        binding.layoutSelfMode.clRootView.animate()
                            .alpha(0f) // 투명도 0으로 변경 (완전 투명)
                            .setDuration(500)
                            .start()
                    }
                }
            }
        }

        viewModel.clickedPosition.observe(viewLifecycleOwner) { position ->
            position?.let {
                scrollToPosition(it)
            }
        }
    }

    private fun updateRecyclerView(trimCategories: List<TrimCategory>) {
        adapter.updateTrims(trimCategories)
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