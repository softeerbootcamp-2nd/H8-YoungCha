package com.youngcha.ohmycarset.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelectAdapter
import com.youngcha.ohmycarset.databinding.ActivityMainBinding
import com.youngcha.ohmycarset.model.TrimCategory
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.ui.customview.HeaderToolBar
import com.youngcha.ohmycarset.ui.fragment.GuideModeFragmentDirections
import com.youngcha.ohmycarset.ui.fragment.TrimSelfModeFragmentDirections
import com.youngcha.ohmycarset.ui.interfaces.OnHeaderToolbarClickListener
import com.youngcha.ohmycarset.ui.listener.CustomScrollChangeListener
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

        binding.nsvSelfTrimScroll.setOnScrollChangeListener(
            CustomScrollChangeListener(binding.ivDropDownTrim, binding.tvDetailInfoTxt)
        )
    }

    private fun observeViewModel() {
        viewModel.trimCategoryState.observe(this) { trimState ->
            updateRecyclerView(trimState.trimCategories)

            if (!trimState.isFirstLoad) {
                handleNavigation(trimState.currTrimCategory)
            }
        }

        viewModel.clickedPosition.observe(this) { position ->
            position?.let {
                scrollToPosition(it)
            }
        }
    }

    private fun updateRecyclerView(trimCategories: List<TrimCategory>) {
        adapter.updateTrims(trimCategories)
    }

    private fun handleNavigation(selectedTrimCategory: TrimCategory) {
        val currentDestinationId = findNavController(R.id.fc_nav_host).currentDestination?.id
        val action = when (selectedTrimCategory.type) {
            TrimType.SELF -> getSelfAction(currentDestinationId, selectedTrimCategory)
            TrimType.GUIDE -> getGuideAction(currentDestinationId, selectedTrimCategory)
        }
        action?.let { navigateTo(it) }
    }

    private fun getSelfAction(currentDestinationId: Int?, selectedTrimCategory: TrimCategory): NavDirections? {
        return when (currentDestinationId) {
            R.id.trimSelfModeFragment -> TrimSelfModeFragmentDirections.actionSelfModeFragmentSelf(
                selectedTrimCategory.name
            )

            else -> GuideModeFragmentDirections.actionGuideModeFragmentToSelfModeFragment(
                selectedTrimCategory.name
            )
        }
    }

    private fun getGuideAction(currentDestinationId: Int?, selectedTrimCategory: TrimCategory): NavDirections? {
        return when (currentDestinationId) {
            R.id.guideModeFragment -> GuideModeFragmentDirections.actionGuideModeFragmentSelf(
                selectedTrimCategory.name
            )

            else -> TrimSelfModeFragmentDirections.actionSelfModeFragmentToGuideModeFragment(
                selectedTrimCategory.name
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