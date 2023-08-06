package com.youngcha.ohmycarset.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EdgeEffect
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.ui.adapter.TrimSelectAdapter
import com.youngcha.ohmycarset.databinding.ActivityMainBinding
import com.youngcha.ohmycarset.model.Trim
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.ui.fragment.GuideModeFragmentDirections
import com.youngcha.ohmycarset.ui.fragment.SelfModeFragmentDirections
import com.youngcha.ohmycarset.util.MILLISECONDS_PER_INCH
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TrimSelectViewModel by viewModels()
    private lateinit var adapter: TrimSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeViewModel()
    }

    private fun init() {
        adapter = TrimSelectAdapter(viewModel)

        binding.rvTrimSelect.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MainActivity.adapter
        }

        binding.nsvSelfTrimScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // 아래로 스크롤될 경우
            if (scrollY > oldScrollY) {
                // 투명도 애니메이션 (1f에서 0f로)
                val fadeOut = ObjectAnimator.ofFloat(binding.ivDropDownTrim, "alpha", 1f, 0f)
                fadeOut.duration = 300 // 300ms 동안
                fadeOut.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.ivDropDownTrim.visibility = View.GONE
                    }
                })
                fadeOut.start()

                val fadeOutText = ObjectAnimator.ofFloat(binding.tvDetailInfoTxt, "alpha", 1f, 0f)
                fadeOutText.duration = 300 // 300ms 동안
                fadeOutText.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.tvDetailInfoTxt.visibility = View.GONE
                    }
                })
                fadeOutText.start()
            }
            // 위로 스크롤될 경우
            else if (scrollY < oldScrollY) {
                // 뷰가 보여지기 전에 투명도를 0으로 설정
                binding.ivDropDownTrim.alpha = 0f
                binding.tvDetailInfoTxt.alpha = 0f
                binding.ivDropDownTrim.visibility = View.VISIBLE
                binding.tvDetailInfoTxt.visibility = View.VISIBLE

                // 투명도 애니메이션 (0f에서 1f로)
                val fadeIn = ObjectAnimator.ofFloat(binding.ivDropDownTrim, "alpha", 0f, 1f)
                fadeIn.duration = 300 // 300ms 동안
                fadeIn.start()

                val fadeInText = ObjectAnimator.ofFloat(binding.tvDetailInfoTxt, "alpha", 0f, 1f)
                fadeInText.duration = 300 // 300ms 동안
                fadeInText.start()
            }
        })
    }

    private fun observeViewModel() {
        viewModel.trimState.observe(this) { trimState ->
            updateRecyclerView(trimState.trims)

            if (!trimState.isFirstLoad) {
                handleNavigation(trimState.currTrim)
            }
        }

        viewModel.clickedPosition.observe(this) { position ->
            position?.let {
                scrollToPosition(it)
            }
        }
    }

    private fun updateRecyclerView(trims: List<Trim>) {
        adapter.updateTrims(trims)
    }

    private fun handleNavigation(selectedTrim: Trim) {
        val currentDestinationId = findNavController(R.id.fc_nav_host).currentDestination?.id
        val action = when (selectedTrim.type) {
            TrimType.SELF -> getSelfAction(currentDestinationId, selectedTrim)
            TrimType.GUIDE -> getGuideAction(currentDestinationId, selectedTrim)
        }
        action?.let { navigateTo(it) }
    }

    private fun getSelfAction(currentDestinationId: Int?, selectedTrim: Trim): NavDirections? {
        return when (currentDestinationId) {
            R.id.selfModeFragment -> SelfModeFragmentDirections.actionSelfModeFragmentSelf(
                selectedTrim.name
            )

            else -> GuideModeFragmentDirections.actionGuideModeFragmentToSelfModeFragment(
                selectedTrim.name
            )
        }
    }

    private fun getGuideAction(currentDestinationId: Int?, selectedTrim: Trim): NavDirections? {
        return when (currentDestinationId) {
            R.id.guideModeFragment -> GuideModeFragmentDirections.actionGuideModeFragmentSelf(
                selectedTrim.name
            )

            else -> SelfModeFragmentDirections.actionSelfModeFragmentToGuideModeFragment(
                selectedTrim.name
            )
        }
    }

    private fun navigateTo(action: NavDirections) {
        findNavController(R.id.fc_nav_host).navigate(action)
    }

    private fun scrollToPosition(position: Int) {
        (binding.rvTrimSelect.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
            val smoothScroller = object : LinearSmoothScroller(this@MainActivity) {
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
}