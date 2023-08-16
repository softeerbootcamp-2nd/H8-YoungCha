package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel

class CarCustomizationFragment : Fragment() {
    private var _binding: FragmentCarCustomizationBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")

    private val carViewModel: CarCustomizationViewModel by viewModels()
    private var currentTabIndex = 0

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
            setupMainTabSelectionListener()
            setupSubTabs()
            observeViewModel()
            setupListener()
        }
    }

    private fun setupListener() {
        binding.htbHeaderToolbar.listener  = object: OnHeaderToolbarClickListener {
            override fun onExitClick() {
                showSnackbar("Exit clicked!")
            }

            override fun onModeChangeClick() {
                val dialog = ButtonDialogView(requireContext(), ButtonDialog("Vertical", R.drawable.ic_change, "모델을 변경하시겠어요?", ButtonHorizontal(
                    "", 1, "", ""), ButtonVertical(carViewModel.currentType.value!!)
                )
                )

                dialog.setOnVerticalButtonClickListener { value ->
                    if (value == carViewModel.currentType.value!!) return@setOnVerticalButtonClickListener
                    carViewModel.updateCurrentType(value)
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
            carViewModel.updateTabInformation(car)
        }

        carViewModel.subOptionViewType.observe(viewLifecycleOwner) {
            updateDataContainerWithCurrentTab()
        }

        carViewModel.currentOptionList.observe(viewLifecycleOwner) { optionList ->
            handleOptionListUpdates(optionList)
        }

        carViewModel.mainOptionsTabs.observe(viewLifecycleOwner) { tabs ->
            tabs.forEach { tabName ->
                binding.tlMainOptionTab.addTab(binding.tlMainOptionTab.newTab().setText(tabName))
            }
        }

        carViewModel.additionalTabs.observe(viewLifecycleOwner) { tabs ->
            tabs.forEach { tabName ->
                binding.tlMainOptionTab.addTab(binding.tlMainOptionTab.newTab().setText(tabName))
            }
        }
    }

    private fun updateDataContainerWithCurrentTab() {
        val currentTab = binding.tlSubOptionTab.getTabAt(currentTabIndex)
        currentTab?.let { tab ->
            updateDataContainer(tab)
        }
    }

    private fun handleOptionListUpdates(optionList: List<OptionInfo>) {
        if (optionList.size <= 2) {
            (binding.vpOptionContainer.adapter as? CarOptionPagerAdapter)?.clearOptions()
            return
        }

        val currentKey = carViewModel.currentComponentName.value
        (binding.vpOptionContainer.adapter as? CarOptionPagerAdapter)?.setOptions(
            optionList,
            "",
            currentKey
        )
        val selectedOptions = carViewModel.isSelectedOptions(currentKey!!) ?: listOf()
        val position = optionList.indexOf(selectedOptions.getOrNull(0)).takeIf { it != -1 } ?: 0
        binding.vpOptionContainer.setCurrentItem(position, false)
    }

    private fun setupMainTabSelectionListener() {
        binding.tlMainOptionTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabName = tab?.text?.replace(Regex("^\\d+\\s"), "") ?: "Unknown"
                carViewModel.setCurrentComponentName(tabName)

                when (tabName) {
                    OPTION_SELECTION -> handleOptionSelectionTab()
                    else -> handleDefaultTab()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    // 선택 옵션 탭이 선택되었을 때 처리.
    private fun handleOptionSelectionTab() {
        binding.tlSubOptionTab.getTabAt(0)?.let { updateDataContainer(it) }
        carViewModel.setOptionSelectionTabValues()
    }

    // 기본 탭이 선택되었을 때 처리.
    private fun handleDefaultTab() {
        carViewModel.setDefaultTabValues()
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
        setupSubOptionTabs()
        addTabSelectedListener()
    }

    private fun setupSubOptionTabs() {
        val tabNames = carViewModel.getSubOptionKeys()

        for (name in tabNames) {
            binding.tlSubOptionTab.addTab(createCustomTab(name))
        }
    }

    private fun createCustomTab(name: String): TabLayout.Tab {
        val tab = binding.tlSubOptionTab.newTab()
        val customView = layoutInflater.inflate(R.layout.view_custom_tab_name, null)
        customView.findViewById<TextView>(R.id.tv_tab_name).text = name
        tab.customView = customView
        return tab
    }

    private fun addTabSelectedListener() {
        binding.tlSubOptionTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentTabIndex = tab.position
                updateDataContainer(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun updateDataContainer(tab: TabLayout.Tab) {
        val tabName = getTabName(tab)

        val optionInfos = carViewModel.getOptionInfoByKey(tabName)
        if (optionInfos != null) {
            if (carViewModel.subOptionButtonVisible.value == 0) {
                displayOnRecyclerView(optionInfos, tabName)
            } else {
                displayOnViewPager(optionInfos, tabName)
            }
        }

        TabLayoutMediator(binding.tbOptionIndicator, binding.vpOptionContainer) { _, _ -> }.attach()
    }

    private fun getTabName(tab: TabLayout.Tab): String {
        val customView = tab.customView
        return customView?.findViewById<TextView>(R.id.tv_tab_name)?.text?.toString() ?: ""
    }

    private fun displayOnRecyclerView(optionInfos: List<OptionInfo>, tabName: String) {
        val adapter = CarOptionPagerAdapter(carViewModel)
        adapter.isDisplayingInPager = false
        binding.rvSubOptionList.adapter = adapter
        adapter.setOptions(optionInfos, OPTION_SELECTION, tabName)
    }

    private fun displayOnViewPager(optionInfos: List<OptionInfo>, tabName: String) {
        val adapter = CarOptionPagerAdapter(carViewModel)
        adapter.isDisplayingInPager = true
        binding.vpOptionContainer.adapter = adapter
        val selectedOptions = carViewModel.isSelectedOptions(tabName) ?: listOf()
        val position = optionInfos.indexOfFirst { it == selectedOptions.firstOrNull() }.takeIf { it != -1 } ?: 0
        binding.vpOptionContainer.setCurrentItem(position, false)
        adapter.setOptions(optionInfos, OPTION_SELECTION, tabName)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}