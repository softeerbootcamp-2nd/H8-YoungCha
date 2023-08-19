package com.youngcha.ohmycarset.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.youngcha.ohmycarset.ui.adapter.viewpager.CarOptionPagerAdapter
import com.youngcha.ohmycarset.ui.customview.ButtonDialogView
import com.youngcha.ohmycarset.ui.interfaces.OnHeaderToolbarClickListener
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import com.youngcha.ohmycarset.util.setupImageSwipe
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel
import kotlinx.coroutines.launch

class CarCustomizationFragment : Fragment() {
    private var _binding: FragmentCarCustomizationBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")

    private val carViewModel: CarCustomizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarCustomizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        }
        binding.vMainTabLayoutOverlay.setOnTouchListener { _, _ -> true }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        binding.htbHeaderToolbar.listener  = object: OnHeaderToolbarClickListener {
            override fun onExitClick() {
                showSnackbar("Exit clicked!")
            }

            override fun onModeChangeClick() {
                val dialog = ButtonDialogView(requireContext(), ButtonDialog("Vertical", R.drawable.ic_change, "모드를 변경하시겠어요?", ButtonHorizontal(
                    "", 1, "", ""), ButtonVertical(carViewModel.currentType.value!!)
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
        carViewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            carViewModel.updateTabInfo(car)
            val firstKey = car.mainOptions.first().keys.firstOrNull()
            carViewModel.setCurrentComponentName(firstKey!!)
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

        carViewModel.currentSubTabs.observe(viewLifecycleOwner) {tabs ->
            tabs.forEach { tabName ->
                binding.tlSubOptionTab.addTab(createCustomTab(tabName))
            }
        }

        carViewModel.estimateViewVisible.observe(viewLifecycleOwner) {
            if (it == 1) {
                particleAnimation()
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

        carViewModel.customizedParts.observe(viewLifecycleOwner) {
            Log.d("로그1", it.toString())
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
        adapter.setOptions(optionInfos, OPTION_SELECTION, tabName, false, carViewModel.currentType.value)
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
        adapter.setOptions(optionInfos, OPTION_SELECTION, tabName, true, carViewModel.currentType.value)
    }

    private fun particleAnimation() {
        viewLifecycleOwner.lifecycleScope.launch {
            val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

            val animationSet = AnimationSet(true)
            animationSet.addAnimation(slideInAnimation)
            animationSet.addAnimation(fadeInAnimation)

            binding.fragmentEstimate.ivParticle.visibility = View.VISIBLE
            binding.fragmentEstimate.ivParticle.startAnimation(animationSet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}