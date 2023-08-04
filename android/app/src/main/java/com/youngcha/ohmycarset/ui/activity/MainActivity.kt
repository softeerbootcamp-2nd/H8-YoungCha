package com.youngcha.ohmycarset.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
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
        val trims = listOf(
            Trim(TrimType.GUIDE, "#Guide Mode", "#나만을 위한 팰리세이드", true),
            Trim(TrimType.SELF, "Exclusive", "#기본에 충실", false),
            Trim(TrimType.SELF, "Le Blanc", "#베스트셀러", false),
            Trim(TrimType.SELF, "Prestige", "#부담없는 고급감", false),
            Trim(TrimType.SELF, "Calligraphy", "#최고를 원한다면", false)
        )

        adapter = TrimSelectAdapter(trims, viewModel)

        viewModel.setTrims(trims)

        binding.rvTrimSelect.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MainActivity.adapter
        }

    }

    private fun observeViewModel() {
        viewModel.trimState.observe(this) { trimState ->
            updateRecyclerView(trimState.trims)
            handleNavigation(trimState.currTrim)
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
            TrimType.SELF -> getSelfAction(currentDestinationId)
            TrimType.GUIDE -> getGuideAction(currentDestinationId)
        }
        action?.let { navigateTo(it) }
    }

    private fun getSelfAction(currentDestinationId: Int?) = when (currentDestinationId) {
        R.id.selfModeFragment -> SelfModeFragmentDirections.actionSelfModeFragmentSelf()
        else -> GuideModeFragmentDirections.actionGuideModeFragmentToSelfModeFragment()
    }

    private fun getGuideAction(currentDestinationId: Int?) = when (currentDestinationId) {
        R.id.guideModeFragment -> GuideModeFragmentDirections.actionGuideModeFragmentSelf()
        else -> SelfModeFragmentDirections.actionSelfModeFragmentToGuideModeFragment()
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