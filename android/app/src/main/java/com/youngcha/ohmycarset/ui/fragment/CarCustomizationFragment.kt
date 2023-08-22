package com.youngcha.ohmycarset.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentCarCustomizationBinding
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.model.dialog.ButtonDialog
import com.youngcha.ohmycarset.model.dialog.ButtonHorizontal
import com.youngcha.ohmycarset.model.dialog.ButtonVertical
import com.youngcha.ohmycarset.ui.adapter.recyclerview.EstimateDetailAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.TrimSelfModeOptionAdapter
import com.youngcha.ohmycarset.ui.adapter.viewpager.CarOptionPagerAdapter
import com.youngcha.ohmycarset.ui.customview.ButtonDialogView
import com.youngcha.ohmycarset.ui.interfaces.OnHeaderToolbarClickListener
import com.youngcha.ohmycarset.util.AnimationUtils.animateValueChange
import com.youngcha.ohmycarset.util.AnimationUtils.explodeView
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import com.youngcha.ohmycarset.util.setupImageSwipeWithScrollView
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class CarCustomizationFragment : Fragment() {
    private var _binding: FragmentCarCustomizationBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")

    private lateinit var detailAdapterMain: EstimateDetailAdapter
    private lateinit var detailAdapterSub: EstimateDetailAdapter
    private lateinit var trimSelfModeOptionAdapter: TrimSelfModeOptionAdapter
    private val carViewModel: CarCustomizationViewModel by viewModels()

    private lateinit var mode: String
    private lateinit var startPoint: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarCustomizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mode = arguments?.getString("mode") ?: ""
        startPoint = arguments?.getString("startPoint") ?: ""
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            viewModel = carViewModel
            lifecycleOwner = this@CarCustomizationFragment
            vpOptionContainer.adapter = CarOptionPagerAdapter(carViewModel)
            attachTabLayoutMediator()
            setupRecyclerView()
            setupSubTabs()
            observeViewModel()
            setupListener()
            estimateSubTabs()
        }
        binding.vMainTabLayoutOverlay.setOnTouchListener { _, _ -> true }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        val images = List(60) { id ->
            resources.getIdentifier("image_0${id + 1}", "drawable", requireContext().packageName)
        }
        binding.layoutEstimate.ivEstimateDone.setupImageSwipeWithScrollView(images)

        binding.htbHeaderToolbar.listener = object : OnHeaderToolbarClickListener {
            override fun onExitClick() {
                findNavController().navigate(R.id.action_makeCarFragment_to_trimSelectFragment)
            }

            override fun onModeChangeClick() {
                val dialog = ButtonDialogView(
                    requireContext(), ButtonDialog(
                        "Vertical", R.drawable.ic_change, "모드를 변경하시겠어요?", ButtonHorizontal(
                            "", 1, "", ""
                        ), ButtonVertical(carViewModel.currentType.value!!)
                    )
                )

                dialog.setOnVerticalButtonClickListener { value ->
                    if (value == carViewModel.currentType.value!!) return@setOnVerticalButtonClickListener
                    carViewModel.updateCurrentType(value)
                    // 탭을 첫 번째로 이동
                    binding.tlMainOptionTab.getTabAt(0)?.select()
                }

                dialog.show()
            }

            override fun onDictionaryOffClick() {
                showSnackbar("Dictionary off clicked!")
            }

            override fun onModelChangeClick() {
                showSnackbar("Model change clicked!")
            }

            private fun showSnackbar(message: String) {
                Snackbar.make(binding.htbHeaderToolbar, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tbOptionIndicator, binding.vpOptionContainer) { _, _ ->
        }.attach()
    }

    private fun observeViewModel() {
        carViewModel.startAnimationEvent.observe(viewLifecycleOwner) { feedbackViewId ->
            toggleButtonsState(false)

            val targetView = when (feedbackViewId) {
                "fv_component_option_1" -> binding.fvComponentOption1
                "fv_component_option_2" -> binding.fvComponentOption2
                "fv_vp_container" -> binding.fvVpContainer
                else -> null
            }

            val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
            fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    targetView?.visibility = View.INVISIBLE
                    carViewModel.handleTabChange(1)
                    toggleButtonsState(true)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

            targetView?.onAnimationEndListener = {
                targetView?.startAnimation(fadeOutAnimation)
            }

            when (feedbackViewId) {
                "fv_component_option_1" -> {
                    binding.fvComponentOption1.visibility = View.VISIBLE
                    binding.fvComponentOption1.startFeedbackAnimation()
                }

                "fv_component_option_2" -> {
                    binding.fvComponentOption2.visibility = View.VISIBLE
                    binding.fvComponentOption2.startFeedbackAnimation()
                }

                "fv_vp_container" -> {
                    binding.fvVpContainer.visibility = View.VISIBLE
                    binding.fvVpContainer.startFeedbackAnimation()
                }

                "estimate_summary" -> {
                    //  Coroutine을 사용하여 일정 시간 후에 handleTabChange(1)을 호출
                    lifecycleScope.launch {
                        delay(1000) // 1초 대기
                        carViewModel.handleTabChange(1)
                        toggleButtonsState(true)
                    }
                }
            }
        }

        carViewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            val firstKey = car.mainOptions.first().keys.firstOrNull()
            if (mode == "GuideMode") {
                carViewModel.initCarCustomizationViewModel(mode, startPoint)
                startPoint = "null"
            }

            if (carViewModel.currentType.value != "GuideMode") {
                carViewModel.setCurrentComponentName(firstKey!!)
            }
        }

        carViewModel.subOptionViewType.observe(viewLifecycleOwner) {
            carViewModel.updateDataContainer()
        }

        // 메인 탭 옵션 리스트 (ex: 파워 트레인 -> [디젤], [가솔린])
        carViewModel.currentOptionList.observe(viewLifecycleOwner) { optionList ->
            handleOptionListUpdates(optionList)
        }

        carViewModel.currentMainTabs.observe(viewLifecycleOwner) { tabs ->
            tabs.forEach { tabName ->
                binding.tlMainOptionTab.addTab(binding.tlMainOptionTab.newTab().setText(tabName))
            }
        }

        carViewModel.currentSubTabs.observe(viewLifecycleOwner) { tabs ->
            tabs.forEach { tabName ->
                binding.tlSubOptionTab.addTab(createCustomTab(tabName))
            }
        }

        carViewModel.currentEstimateSubTabs.observe(viewLifecycleOwner) { tabs ->
            binding.layoutEstimate.lyDetail.tlOption.removeAllTabs()
            tabs.forEach { tabName ->
                binding.layoutEstimate.lyDetail.tlOption.addTab(createCustomTabInEstimate(tabName))
            }
        }

        carViewModel.estimateViewVisible.observe(viewLifecycleOwner) {
            if (it == 1) {
                view?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
                    override fun onGlobalLayout() {
                        // parent의 너비와 높이는 0이상인경우
                        explodeView(binding.flParticleContainer)
                        //리스너 제거
                        view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    }
                })
                carViewModel.filteredMainSub()
                carViewModel.filterSubOptions("전체")
            }
        }

        carViewModel.displayOnRecyclerViewOnViewPager.observe(viewLifecycleOwner) { mode ->
            val tabName = carViewModel.currentTabName.value // currentTabName -> main tab + sub tab
            carViewModel.getOptionInfoByKey(tabName!!).let {
                when (mode) {
                    0 -> it?.let { it1 -> displayOnRecyclerView(it1, tabName) }
                    1 -> it?.let { it1 -> displayOnViewPager(it1, tabName) }
                    else -> {}
                }
            }
            attachTabLayoutMediator()
        }

        carViewModel.currentTabPosition.observe(viewLifecycleOwner) {
            binding.tlMainOptionTab.getTabAt(it)?.select()
        }

        carViewModel.customizedParts.observe(viewLifecycleOwner) { customized ->
            val systemOptions: List<OptionInfo>? = customized?.firstOrNull { it.containsKey("시스템") }?.get("시스템")
        }

        carViewModel.estimateSubOptions.observe(viewLifecycleOwner) { subOptions ->
            val emptySubOptions: Map<String, List<OptionInfo>> = emptyMap()
            detailAdapterSub.updateOptionInfo(subOptions ?: emptySubOptions)
        }

        carViewModel.estimateMainOptions.observe(viewLifecycleOwner) { mainOptions ->
            val emptyMainOptions: Map<String, List<OptionInfo>> = emptyMap()
            detailAdapterMain.updateOptionInfo(mainOptions ?: emptyMainOptions)
        }

        carViewModel.customizedParts.observe(viewLifecycleOwner) {
            var totalPrice: Int = 0
            it.forEach { map ->
                map.values.forEach { optionInfos ->
                    optionInfos.forEach { optionInfo ->
                        totalPrice = totalPrice.plus(optionInfo.price)

                    }
                }
            }


            val prevTotalPrice = carViewModel.prevPrice.value
            val currentTotalPrice =
                carViewModel.totalPrice.value?.plus(carViewModel.getMyCarTotalPrice())

            animateValueChange(
                binding.tvEstimatePrice,
                prevTotalPrice!!,
                currentTotalPrice!!,
                requireContext().resources
            ).start()
            carViewModel.prevPrice.value = currentTotalPrice
        }

    }

    // 현재 선택한 탭의 옵션 리스트를 ViewPager에 연결
    private fun handleOptionListUpdates(optionList: List<OptionInfo>) {
        // optionList.size가 2이상이라면 viewpager에 데이터가 보여야함
        if (optionList.size <= 2) {
            (binding.vpOptionContainer.adapter as? CarOptionPagerAdapter)?.clearOptions()
            return
        }

        val currentKey = carViewModel.currentComponentName.value
        (binding.vpOptionContainer.adapter as? CarOptionPagerAdapter)?.setOptions(
            optionList,
            "",
            currentKey,
            true,
            carViewModel.currentType.value
        )
        val selectedOptions = carViewModel.isSelectedOptions(currentKey!!) ?: listOf()
        val position = optionList.indexOf(selectedOptions.getOrNull(0)).takeIf { it != -1 } ?: 0
        binding.vpOptionContainer.setCurrentItem(position, false)
    }

    private fun setupRecyclerView() {
        // LinearLayoutManager 사용하여 수직 방향의 리스트로 설정
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvSubOptionList.layoutManager = linearLayoutManager

        // 아이템 크기가 동적으로 변경되지 않을 경우 성능 향상을 위해 설정
        binding.rvSubOptionList.setHasFixedSize(true)

        // 초기 어댑터 설정 (옵션 데이터가 없는 초기 상태)
        binding.rvSubOptionList.adapter = CarOptionPagerAdapter(carViewModel)


        val linearLayoutManagerForMainOption = LinearLayoutManager(requireContext())
        binding.layoutEstimate.lyDetail.rvMainOption.layoutManager = linearLayoutManagerForMainOption

        detailAdapterMain = EstimateDetailAdapter { optionInfo ->
            // optionInfo에 해당하는 탭으로 이동
            val position = carViewModel.currentMainTabs.value?.indexOf(optionInfo)

            // 찾은 위치로 _currentTabPosition를 업데이트합니다.
            if (position != null && position != -1) {
                carViewModel.updateTapPosition(position, optionInfo)
            }
        }
        binding.layoutEstimate.lyDetail.rvMainOption.adapter  = detailAdapterMain

        val linearLayoutManagerForSubOption = LinearLayoutManager(requireContext())
        binding.layoutEstimate.lyDetail.rvSubOption.layoutManager = linearLayoutManagerForSubOption

        detailAdapterSub = EstimateDetailAdapter { optionInfo ->
            // optionInfo에 해당하는 탭으로 이동
            val position = carViewModel.currentMainTabs.value?.indexOf(optionInfo)

            // 찾은 위치로 _currentTabPosition를 업데이트합니다.
            if (position != null && position != -1) {
                carViewModel.updateTapPosition(position, optionInfo)
            } else {
                carViewModel.updateTapPosition(6, "옵션 선택")
            }
        }
        binding.layoutEstimate.lyDetail.rvSubOption.adapter = detailAdapterSub

    }

    private fun setupSubTabs() {
        // 옵션 선택에서 sub tab이 클릭되었을때
        binding.tlSubOptionTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                carViewModel.currentSubTabPosition.value = tab.position
                carViewModel.updateDataContainer()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun estimateSubTabs() {
        binding.layoutEstimate.lyDetail.tlOption.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val tvTabName = customView?.findViewById<TextView>(R.id.tv_tab_name)
                val tabName = tvTabName?.text.toString()

                when (carViewModel.estimateSubTabType.value) {
                    "selectOption" -> carViewModel.filterOptionsByTabName(tabName)
                    "basicOption" -> carViewModel.filterSubOptions(tabName)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createCustomTabInEstimate(name: String): TabLayout.Tab {
        val tab = binding.layoutEstimate.lyDetail.tlOption.newTab()
        val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
        customView.findViewById<TextView>(R.id.tv_tab_name).text = name
        tab.customView = customView
        return tab
    }

    private fun createCustomTab(name: String): TabLayout.Tab {
        val tab = binding.tlSubOptionTab.newTab()
        val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
        customView.findViewById<TextView>(R.id.tv_tab_name).text = name
        tab.customView = customView
        return tab
    }


    // sub option 상태에서만 가능
    private fun displayOnRecyclerView(optionInfos: List<OptionInfo>, tabName: String) {
        val adapter = CarOptionPagerAdapter(carViewModel)
        binding.rvSubOptionList.adapter = adapter
        adapter.setOptions(
            optionInfos,
            OPTION_SELECTION,
            tabName,
            false,
            carViewModel.currentType.value
        )
    }

    // main & sub 전부 가능
    private fun displayOnViewPager(optionInfos: List<OptionInfo>, tabName: String) {
        val adapter = CarOptionPagerAdapter(carViewModel)
        binding.vpOptionContainer.adapter = adapter
        val selectedOptions = carViewModel.isSelectedOptions(tabName) ?: listOf()
        val position =
            optionInfos.indexOfFirst { it == selectedOptions.firstOrNull() }.takeIf { it != -1 }
                ?: 0
        binding.vpOptionContainer.setCurrentItem(position, false)
        adapter.setOptions(
            optionInfos,
            OPTION_SELECTION,
            tabName,
            true,
            carViewModel.currentType.value
        )
    }

    fun Fragment.toggleButtonsState(isEnabled: Boolean) {
        binding.btnNext.isEnabled = isEnabled
        binding.btnPrev.isEnabled = isEnabled
        binding.vpOptionContainer.isEnabled = isEnabled
        binding.rvSubOptionList.isEnabled = isEnabled
        binding.btnComponentOption1.isEnabled = isEnabled
        binding.btnComponentOption2.isEnabled = isEnabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}