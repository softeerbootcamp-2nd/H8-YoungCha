package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.enums.AdditionalTab
import com.youngcha.ohmycarset.data.model.TrimSelfMode
import com.youngcha.ohmycarset.data.model.TrimSelfModeOption
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo
import com.youngcha.ohmycarset.data.repository.CategoryRepository
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import com.youngcha.ohmycarset.data.repository.SelfModeRepository
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import com.youngcha.ohmycarset.util.getColorCodeFromName
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarCustomizationViewModel(
    private val repository: SelfModeRepository,
    private val guideModeRepository: GuideModeRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // 자동차 정보 관련 변수들
    // 관련된 변수: selectedCar, currentComponentName, customizedParts
    private val _selectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> = _selectedCar

    val carObserver = Observer<Car> { newCar ->
        _selectedCar.value = newCar
    }

    val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val carRotateView = MutableLiveData<Int>(0)

    fun setLoadingState(state: Boolean) {
        _isLoading.value = state
    }

    private val _currentExteriorColor =  MutableLiveData<String>("WC9")
    val currentExteriorColor: LiveData<String> = _currentExteriorColor
    val currentExteriorColorFirstUrl = MutableLiveData<String>("https://www.hyundai.com/contents/vr360/LX06/exterior/WC9/001.png")
    val currentInteriorColorUrl = MutableLiveData<String>()

    val categories = MutableLiveData<List<Category>>()

    private val _currentComponentName = MutableLiveData<String>("파워 트레인")
    val currentComponentName: LiveData<String> = _currentComponentName

    private val _customizedParts = MutableLiveData<List<Map<String, List<OptionInfo>>>>()
    val customizedParts: LiveData<List<Map<String, List<OptionInfo>>>> = _customizedParts
    fun logCustomizedParts() {
        val parts = _customizedParts.value

        parts?.let {
            val logBuilder = StringBuilder("Customized Parts:\n\n")

            for (partMap in it) {
                for ((key, valueList) in partMap) {
                    logBuilder.append("Component: $key\n")

                    for (optionInfo in valueList) {
                        logBuilder.append("  Option: ${optionInfo.name}, Checked: ${optionInfo.checked}\n")
                    }

                    logBuilder.append("\n")
                }
            }

            Log.d("로그", logBuilder.toString())
        } ?: run {
            Log.d("로그", "No parts available.")
        }
    }


    // 선택 모드 관련 변수
    // 관련된 변수: currentType
    private val _currentType = MutableLiveData<String>("SelfMode")
    val currentType: LiveData<String> = _currentType

    // 현재 선택된 옵션
    val currentSelectedOption = MutableLiveData<OptionInfo>()
    val subOptionImage = MutableLiveData<String>()

    // 옵션 선택 UI 관련 변수들
    // 관련된 변수: componentOption1Visibility, componentOption2Visibility, horizontalButtonVisible, swipeButtonVisible, subOptionButtonVisible, subOptionViewTypeChangeButton, subOptionViewType
    val componentOption1Visibility = MutableLiveData<Int>()
    val componentOption2Visibility = MutableLiveData<Int>()
    val horizontalButtonVisible = MutableLiveData<Int>(0)
    val swipeButtonVisible = MutableLiveData<Int>(0)
    val subOptionButtonVisible = MutableLiveData<Int>(1)
    val subOptionViewTypeChangeButton = MutableLiveData<Int>(0)
    val subOptionViewType = MutableLiveData<Int>(0)

    // 탭 UI 관련 변수들
    // 관련된 변수: currentTabName, currentTabPosition, currentSubTabPosition, currentMainTabs, currentSubTabs
    val currentTabName = MutableLiveData<String>()
    private val _currentTabPosition = MutableLiveData<Int>(0)
    val currentTabPosition: LiveData<Int> = _currentTabPosition
    val currentSubTabPosition = MutableLiveData<Int>(0)
    val currentMainTabs = MutableLiveData<List<String>>()
    private val _currentSubTabs = MutableLiveData<List<String>>()
    val currentSubTabs: LiveData<List<String>> = _currentSubTabs

    private val _currentEstimateSubTabs = MutableLiveData<List<String>>()
    val currentEstimateSubTabs: LiveData<List<String>> = _currentEstimateSubTabs

    private val _estimateSubOptions = MutableLiveData<Map<String, List<OptionInfo>>?>()
    val estimateSubOptions: LiveData<Map<String, List<OptionInfo>>?> = _estimateSubOptions

    private val _estimateMainOptions = MutableLiveData<Map<String, List<OptionInfo>>?>()
    val estimateMainOptions: LiveData<Map<String, List<OptionInfo>>?> = _estimateMainOptions

    // Sub Tab selectOption || basicOption
    val estimateSubTabType = MutableLiveData<String>("selectOption")

    private val _trimSelfModeData = MutableLiveData<TrimSelfMode>()
    val trimSelfModeData: LiveData<TrimSelfMode> = _trimSelfModeData

    val filteredOptions: MutableLiveData<List<TrimSelfModeOption>> = MutableLiveData()

    // 뷰페이지 및 리사이클러뷰 표시 관련 변수
    // 관련된 변수: displayOnRecyclerViewOnViewPager
    private val _displayOnRecyclerViewAndViewPager = MutableLiveData<Int>(0)
    val displayOnRecyclerViewOnViewPager: LiveData<Int> = _displayOnRecyclerViewAndViewPager

    // 추정 보기 관련 변수
    // 관련된 변수: estimateViewVisible
    private val _estimateViewVisible = MutableLiveData<Int>(0)
    val estimateViewVisible: LiveData<Int> = _estimateViewVisible

    // 외관 버튼 변화 관련 변수
    // 관련된 변수: exteriorButtonChange
    val exteriorButtonChange = MutableLiveData<Int>(1)

    val currentOptionList = MediatorLiveData<List<OptionInfo>>()

    // 선택 완료 시 애니메이션 관련 변수
    private val _startAnimationEvent = MutableLiveData<String>()
    val startAnimationEvent: LiveData<String>
        get() = _startAnimationEvent


    // 현재 가격 애니메이션 관련 변수
    val totalPrice = MutableLiveData<Int>(0)
    val prevPrice = MutableLiveData<Int>(0)

    private val initialization = CompletableDeferred<Unit>()
    // --- 프래그먼트 초기 시점 함수 ---

    init {
        repository.car.observeForever(carObserver)
        viewModelScope.launch(Dispatchers.IO) {

            // 백그라운드에서 데이터 가져오기
            val deferred = async { setupTabs() }
            deferred.await()

            withContext(Dispatchers.Main) {
                // 메인 스레드에서 LiveData 업데이트
                currentMainTabs.value = categoryRepository.mainCategories.value
                _currentSubTabs.value = categoryRepository.subCategories.value
                _currentTabPosition.value = 0
                currentTabName.value = currentMainTabs.value!![0]
                currentSubTabPosition.value = 0
                totalPrice.value = 41980000
                categories.value = categoryRepository.getAllSubCategories()
            }
            initialization.complete(Unit)
        }

        currentOptionList.addSource(_currentComponentName) { componentName ->
            val optionList =
                _selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
                    ?.get(componentName) ?: emptyList()
            currentOptionList.value = optionList
        }
    }


    fun startGuideMode(guideParam: GuideParam) {
        viewModelScope.launch(Dispatchers.IO) {

            val fetchedCategories = categoryRepository.getAllSubCategories()
            fetchedCategories?.let {
                guideModeRepository.fetchAllGuideDataAndSetCar(guideParam, it)
                withContext(Dispatchers.Main) {
                    // 메인 스레드에서 LiveData 업데이트
                    categories.value = it
                    _selectedCar.value = guideModeRepository.car.value

                    _currentType.value = "GuideMode"
                    _selectedCar.value?.let { selectedCarValue ->

                        // 1. mainOptions 처리
                        val customizedMainOptions =
                            selectedCarValue.mainOptions.mapNotNull { optionMap ->
                                optionMap.entries.mapNotNull { entry ->
                                    val key = entry.key
                                    val values = entry.value.filter { it.checked }


                                    // 내장 색상에 대한 로직 추가
                                    if(key == "내장 색상" && values.isNotEmpty()) {
                                        // 체크된 값 중 첫번째 항목의 mainImage 값을 currentInteriorColorUrl에 할당
                                        currentInteriorColorUrl.value = values[0].mainImage
                                    }

                                    if (key == "외장 색상" && values.isNotEmpty()) {
                                        _currentExteriorColor.value = values[0].name
                                        setLoadingState(false)
                                    }

                                    exteriorButtonChange.value = 1

                                    if (values.isNotEmpty()) key to values else null
                                }.toMap()
                            }.filter { it.isNotEmpty() }

                        // 2. subOptions 처리
                        val customizedSubOptions =
                            selectedCarValue.subOptions.mapNotNull { optionMap ->
                                optionMap.entries.mapNotNull { entry ->
                                    val key = entry.key
                                    val values = entry.value.filter { it.checked }

                                    if (values.isNotEmpty()) key to values else null
                                }.toMap()
                            }.filter { it.isNotEmpty() }

                        // 3. 두 결과를 합쳐 customizedParts에 설정
                        val combinedOptions = mutableListOf<Map<String, List<OptionInfo>>>()
                        combinedOptions.addAll(customizedMainOptions)
                        combinedOptions.addAll(customizedSubOptions)

                        _customizedParts.value = combinedOptions

                    }
                }
            } ?: run {
            }
        }
    }

    fun startSelfMode() {
        viewModelScope.launch(Dispatchers.IO) {
            initialization.await()
            withContext(Dispatchers.Main) {
                _customizedParts.value = emptyList()
                _currentTabPosition.value = 0
                currentTabName.value = currentMainTabs.value!![0]
                currentSubTabPosition.value = 0
                _currentType.value = "SelfMode"
                totalPrice.value = 41980000
                repository.setCarComponent(2, "파워 트레인", categories.value!!)
                _selectedCar.value = repository.car.value
            }
        }
    }



    /**
     *  초기 시작 지점
     *  가이드 모드일 경우 부품 미리 생성
     */
    fun initCarCustomizationViewModel(currentType: String, startPoint: String) {
        _currentType.value = currentType
        when (currentType) {
            "GuideMode" -> {
                val lastTab = currentMainTabs.value?.lastOrNull()
                if (startPoint == "start") {
                    logCustomizedParts()
                    currentTabName.value = currentMainTabs.value!![0]
                    updateTapPosition(0, "파워 트레인")
                } else {
                    if (lastTab == "견적 내기") {
                        totalPrice.value = totalPrice.value?.plus(getMyCarTotalPrice())
                        val position = currentMainTabs.value?.indexOf(lastTab)
                        _estimateViewVisible.value = 1
                        _currentTabPosition.value = position!!
                    }
                }
            }
        }
    }

    fun setSubOptionImage(value: Int) {
        when (value) {
            0 -> {
                subOptionImage.value = ""
            }

            1 -> {
                // _selectedCar의 subOptions에서 첫 번째 키에 대응하는 첫 번째 밸류의 mainImage를 가져온다.
                if (_selectedCar.value?.subOptions?.isNotEmpty() == true) {
                    val firstSubOption =
                        _selectedCar.value!!.subOptions[0].values.firstOrNull()?.firstOrNull()
                    val mainImage = firstSubOption?.mainImage
                    subOptionImage.value = mainImage ?: ""
                } else {
                    subOptionImage.value = ""
                }
            }
        }
    }

    private suspend fun setupTabs() {
        categoryRepository.getCategories()
    }

    fun updateTapPosition(position: Int, tabName: String) {
        _currentTabPosition.value = position
        _currentComponentName.value = tabName

        prevPrice.value = totalPrice.value?.minus(getMyCarTotalPrice())
        totalPrice.value = 41980000
        setCurrentComponentName(tabName)
    }


    // --- UI 업데이트 관련 함수 ---

    /**
     * 견적 색상 버튼 업데이트
     */
    fun updateEstimateColorButton(view: View) {
        exteriorButtonChange.value = if (exteriorButtonChange.value == 1) 0 else 1
    }

    /**
     * 현재 타입 업데이트
     */
//    suspend fun updateCurrentType(currentType: String) {
//        _currentType.value = currentType
//        _currentTabPosition.value = 0
//        currentTabName.value = currentMainTabs.value!![0]
//        currentSubTabPosition.value = 0
//        totalPrice.value = 36000000
//        _customizedParts.value = emptyList()
//        _selectedCar.value = Car()
//        categories.value?.let { repository.setCarComponent(2, "파워 트레인", it) }
//      //  setCurrentComponentName(currentTabName.value!!)
//    }

    fun updateCurrentType(currentType: String) {
        viewModelScope.launch {
            _currentType.value = currentType
            _currentTabPosition.value = 0
            currentTabName.value = currentMainTabs.value!![0]
            currentSubTabPosition.value = 0
            totalPrice.value = 41980000
            _customizedParts.value = emptyList()
            _selectedCar.value = Car()

            categories.value?.let {
                repository.setCarComponent(2, "파워 트레인", it)
            }
            // setCurrentComponentName(currentTabName.value!!)
        }
    }


    /**
     * 탭 정보 업데이트
     */
    fun updateTabInfo(car: Car) {
        val mainOptions = car.mainOptions[0].keys.mapIndexed { index, carOptionKey ->
            "${String.format("%02d", index + 1)} $carOptionKey"
        }
        val additionalTabs = AdditionalTab.values().mapIndexed { index, additionalTab ->
            val tabIndex = car.mainOptions[0].keys.size + index + 1
            "${String.format("%02d", tabIndex)} ${additionalTab.displayName}"
        }
        currentMainTabs.value = (mainOptions + additionalTabs).map {
            it.replace(Regex("^\\d+\\s"), "")
        }
    }

    /**
     * 탭 변경 핸들러
     */
    fun handleTabChange(increment: Int) {
        val currentTabIndex = currentTabPosition.value ?: 0
        val nextTabIndex = currentTabIndex + increment

        // 범위 확인
        if (nextTabIndex in 0 until currentMainTabs.value!!.size) {
            // 탭 변경 로직
            val tabName = currentMainTabs.value!![nextTabIndex]

            setCurrentComponentName(tabName)
            _currentTabPosition.value = nextTabIndex

            when (tabName) {
                OPTION_SELECTION -> handleSubTab()
                else -> handleMainTab()
            }
        }
    }

    fun getMyCarTotalPrice(): Int {
        var totalPrice = 0

        customizedParts.value?.forEach { map ->
            map.values.forEach { list ->
                list.forEach { optionInfo ->
                    totalPrice += optionInfo.price
                }
            }
        }
        return totalPrice
    }

    /**
     * 서브옵션 변경 핸들러
     */
    fun onSubOptionChanged(view: View) {
        subOptionButtonVisible.value = if (subOptionButtonVisible.value == 1) 0 else 1
        subOptionViewType.value = if (subOptionViewType.value == 1) 0 else 1
    }

    // --- 데이터 설정 및 조회 관련 함수 ---

    fun isAlreadySelectedComponent(componentName: String): Boolean {
        val parts = _customizedParts.value
        if (componentName == OPTION_SELECTION  && currentType.value == "GuideMode"){
            return true
        }
        parts?.let {
            for (partMap in it) {
                if (partMap.containsKey(componentName)) {
                    return true
                }
            }
        }
        return false
    }


    /**
     * 현재 컴포넌트 이름 설정
     */
    fun setCurrentComponentName(componentName: String) {
        viewModelScope.launch {
            if (!isAlreadySelectedComponent(componentName)) {
                when (componentName) {
                    "내장 색상" -> {
                        // 내장 색상일 경우
                        val id =
                            _customizedParts.value?.find { it.keys.contains("왜장 색상") }?.get("왜장 색상")
                                ?.first()?.id
                        if (id != null) {
                            repository.setInteriorColor(2, id)
                        } else {
                            repository.setInteriorColor(2, 13)
                        }
                    }

                    "견적 내기" -> {
                        // "견적 내기" 선택 시 아무 것도 하지 않고 넘어갑니다.
                    }

                    else -> {
                        categories.value?.let { repository.setCarComponent(2, componentName, it) }
                    }
                }
            }

            // Repository 함수 호출이 끝난 후 나머지 로직 실행
            _currentComponentName.value = componentName
            if (componentName == "견적 내기") {
                totalPrice.value = totalPrice.value?.plus(getMyCarTotalPrice())
                _estimateViewVisible.value = 1
            } else {
                _estimateViewVisible.value = 0
            }

            horizontalButtonVisible.value = 0
            swipeButtonVisible.value = 0
            setSubOptionImage(0)

            if (componentName == "외장 색상") {
                setExteriorColor(getOption(currentComponentName.value!!, 0)!!.name)
            }

            if ((componentName == "파워 트레인" || componentName == "바디 타입" || componentName == "구동 방식") && componentName != OPTION_SELECTION) {
                horizontalButtonVisible.value = 1
                alreadySelectedComponent(componentName)
            } else if (componentName == OPTION_SELECTION) {
                setSubOptionImage(1)
                currentSubTabPosition.value = 0
                swipeButtonVisible.value = 1
                updateDataContainer()
            } else {
                swipeButtonVisible.value = 1
            }
        }
    }


    /**
     * 키를 사용하여 서브옵션 정보 가져오기
     */
    fun getOptionInfoByKey(key: String): List<OptionInfo>? {
        val car = _selectedCar.value
        car?.subOptions?.forEach { map ->
            map[key]?.let {
                return it
            }
        }
        return null
    }

    /**
     * 자동차 컴포넌트 추가
     */
    fun addCarComponents(
        componentName: String,
        option: OptionInfo,
        subOptionName: String? = null
    ) {
        val updatedList = _customizedParts.value?.toMutableList() ?: mutableListOf()

        currentSelectedOption.value = option

        if (componentName == "내장 색상") {
            currentInteriorColorUrl.value = option.mainImage
        }

        val keyName = subOptionName ?: componentName
        val index = updatedList.indexOfFirst { it.containsKey(keyName) }

        if (index != -1) {
            // 값이 있다면
            val existingComponent = updatedList[index].toMutableMap() // List

            // Map의 벨류 List<OptionInfo>: 여기서 componentName이 선택 옵션이면 값을 추가하고 아니면 값을 덮어써야함
            val existingOptions = existingComponent[keyName]?.toMutableList() ?: mutableListOf()
            if (componentName == OPTION_SELECTION) {
                existingOptions.add(option)
                existingComponent[keyName] = existingOptions

            } else {
                // 여기에는 값을 덮어써야함 즉 최신 값으로 벨류를 업데이트
                existingComponent[keyName] = listOf(option)
            }

            updatedList[index] = existingComponent
        } else {
            // 값을 찾지 못했다면
            val newComponentMap = mutableMapOf<String, List<OptionInfo>>()
            newComponentMap[keyName] = listOf(option)
            updatedList.add(newComponentMap)
        }
        //  _totalPrice.value = _totalPrice.value?.plus(option.price)
        _customizedParts.value = updatedList
      //  logCustomizedParts()
    }

    /**
     * 자동차 컴포넌트 제거
     */
    fun removeCarComponents(keyName: String, option: OptionInfo) {
        val updatedList = _customizedParts.value?.toMutableList() ?: return
        val index = updatedList.indexOfFirst { it.containsKey(keyName) }
        if (index != -1) {
            val existingComponent = updatedList[index].toMutableMap()
            val existingOptions = existingComponent[keyName]?.toMutableList()

            existingOptions?.remove(option)

            if (existingOptions.isNullOrEmpty()) {
                updatedList.removeAt(index)
            } else {
                existingComponent[keyName] = existingOptions
                updatedList[index] = existingComponent
            }
            //_myCar.postValue(updatedList)
            _customizedParts.value = updatedList
        }
    }

    /**
     * 선택된 옵션 조회
     */
    fun isSelectedOptions(tabName: String): List<OptionInfo>? {
        val car = _customizedParts.value

        val index = car?.indexOfFirst { it.containsKey(tabName) }

        if (index != -1) {
            val myCarToMap = index?.let { car[it] }
            return myCarToMap?.get(tabName)
        }
        return null
    }

    // --- 데이터 헬퍼 함수 ---

    /**
     * 이미 선택된 컴포넌트 처리
     */
    private fun alreadySelectedComponent(componentName: String) {
        val car = _customizedParts.value ?: listOf()
        val index = car.indexOfFirst { it.containsKey(componentName) }
        setComponentOptionVisibility(1, 0)
        if (index != -1) {
            // 이미 선택된 값들이 있다면
            val myCarToList = car[index]

            val myCarToMap = myCarToList[componentName] // 선택된 값
            val option = getOption(componentName, 0) ?: return // 0번 인덱스 옵션 가져오기

            if (option == myCarToMap?.get(0)) {
                onComponentOption1Selected()
            } else {
                onComponentOption2Selected()
            }
        } else {
            onComponentOption1Selected()
        }
    }

    /**
     * 데이터 컨테이너 업데이트
     */
    fun updateDataContainer() {
        currentTabName.value = _currentSubTabs.value?.get(currentSubTabPosition.value!!)
        _displayOnRecyclerViewAndViewPager.value = if (subOptionButtonVisible.value == 0) 0 else 1
    }


    // --- 기타 헬퍼 함수 ---

    /**
     * 메인 탭 처리
     */
    private fun handleMainTab() {
        setDefaultTabValues()
    }

    /**
     * 서브 탭 처리
     */
    private fun handleSubTab() {
        updateDataContainer()
        setOptionSelectionTabValues()
    }

    /**
     * 컴포넌트 옵션의 가시성 설정
     */
    private fun setComponentOptionVisibility(option1Visible: Int, option2Visible: Int) {
        componentOption1Visibility.value = option1Visible
        componentOption2Visibility.value = option2Visible
    }

    /**
     * 컴포넌트 이름을 기반으로 옵션 조회
     */
    private fun getOptionsForComponent(componentName: String): List<OptionInfo>? {
        return selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
            ?.get(componentName)
    }

    // --- 컴포넌트 옵션 선택 관련 함수 ---

    /**
     * 컴포넌트의 0번 인덱스 옵션을 선택했을 때의 처리 함수
     */
    fun onComponentOption1Selected() {
        val componentName = _currentComponentName.value ?: return
        val option = getOption(componentName, 0) ?: return // 0번 인덱스 옵션 가져오기
        addCarComponents(componentName, option)
        setComponentOptionVisibility(1, 0)
    }

    /**
     * 컴포넌트의 1번 인덱스 옵션을 선택했을 때의 처리 함수
     */
    fun onComponentOption2Selected() {
        val componentName = _currentComponentName.value ?: return
        val option = getOption(componentName, 1) ?: return // 1번 인덱스 옵션 가져오기
        addCarComponents(componentName, option)
        setComponentOptionVisibility(0, 1)
    }

    // --- 탭 및 뷰 설정 관련 함수 ---

    /**
     * 옵션 선택 탭 값 설정
     */
    private fun setOptionSelectionTabValues() {
        subOptionViewTypeChangeButton.value = 1
        subOptionButtonVisible.value = 1
    }

    /**
     * 기본 탭 값 설정
     */
    private fun setDefaultTabValues() {
        subOptionViewTypeChangeButton.value = 0 // viewpager <-> recyclerview
        subOptionButtonVisible.value = 1 // main Image visible
    }

    // --- 옵션 정보 조회 관련 함수 ---

    /**
     * 컴포넌트 이름을 기반으로 해당 옵션의 크기(개수) 조회
     */
    private fun getOptionSize(componentName: String): Int {
        return getOptionsForComponent(componentName)?.size ?: 0
    }

    /**
     * 컴포넌트 이름과 인덱스를 사용하여 특정 옵션 정보 조회
     */
    fun getOption(componentName: String, index: Int): OptionInfo? {
        return getOptionsForComponent(componentName)?.getOrNull(index)
    }

    fun getSubOptionKeys(): List<String> {
        val keys = mutableListOf<String>()
        val car = _selectedCar.value
        car?.subOptions?.forEach { map ->
            keys.addAll(map.keys)
        }
        return keys
    }

    fun toggleSubTabType() {
        estimateSubTabType.value = if (estimateSubTabType.value == "selectOption") {
            _currentEstimateSubTabs.value =
                listOf("전체", "성능", "지능형 안전기술", "안전", "외관", "내장", "시트", "편의", "멀티미디어")
            "basicOption"
        } else {
            val subOptionKeys = getSubOptionKeys()
            val estimateTabs = mutableListOf<String>()
            estimateTabs.add("전체")
            estimateTabs.addAll(subOptionKeys)
            _currentEstimateSubTabs.value = estimateTabs
            "selectOption"
        }
    }

    fun filterOptionsByTabName(tabName: String) {
        val allOptions = trimSelfModeData.value?.options ?: emptyList()

        val matchedOptions = if (tabName == "전체") {
            allOptions
        } else {
            allOptions.filter { option ->
                option.type == tabName
            }
        }

        filteredOptions.value = matchedOptions
    }

    fun filterSubOptions(tabName: String) {
        val map: MutableMap<String, List<OptionInfo>> = mutableMapOf()

        if (tabName == "전체") {
            val size = _currentEstimateSubTabs.value?.size

            for (i in 1 until (size ?: 0)) {
                val key = _currentEstimateSubTabs.value?.get(i).toString()
                val subOptionInfoList =
                    _customizedParts.value?.firstOrNull { it.containsKey(key) }?.get(key)
                if (subOptionInfoList != null) {
                    map[key] = subOptionInfoList
                }
            }

            _estimateSubOptions.value = map
        } else {
            _customizedParts.value?.firstOrNull { it.containsKey(tabName) }?.get(tabName)
                ?.let { map[tabName] = it }
            _estimateSubOptions.value = map
        }
    }

    fun filteredMainSub() {
        val size = currentMainTabs.value?.size
        val map: MutableMap<String, List<OptionInfo>> = mutableMapOf()

        for (i in 0 until (size ?: 0)) {
            val key = currentMainTabs.value?.get(i).toString()
            val mainOptionInfoList =
                _customizedParts.value?.firstOrNull { it.containsKey(key) }?.get(key)
            if (mainOptionInfoList != null) {
                map[key] = mainOptionInfoList
            }
        }
        _estimateMainOptions.value = map
    }

    fun setExteriorColor(colorName: String) {
        _currentExteriorColor.value = colorName
    }

    fun setCurrentSelectedOption(option: OptionInfo) {
        currentSelectedOption.value = option
    }

    // 선택 완료 시
    fun executeRandomAnimation() {
        if (currentType.value == "GuideMode") {
            handleTabChange(1)
            return
        }

        val nextTabPosition = _currentTabPosition.value?.plus(1)
        val nextTabName = currentMainTabs.value?.getOrNull(nextTabPosition ?: -1)

        // "견적 내기"와 일치하는지 확인
        if (nextTabName == "견적 내기") {
            _startAnimationEvent.value = "estimate_summary"
            return
        }

        val currentName = _currentComponentName.value
        val partsList = _customizedParts.value
        if (currentName != null && partsList != null) {
            val optionsForComponent = partsList.find { it.containsKey(currentName) }?.get(currentName)
            if (!optionsForComponent.isNullOrEmpty()) {
                currentSelectedOption.value = optionsForComponent[0]

            }
        }

        if ((currentComponentName.value == "파워 트레인" || currentComponentName.value == "바디 타입" || currentComponentName.value == "구동 방식")) {
            if (componentOption1Visibility.value == 1) {
                _startAnimationEvent.value = "fv_component_option_1"
            } else if (componentOption2Visibility.value == 1) {
                _startAnimationEvent.value = "fv_component_option_2"
            }
        } else {
            _startAnimationEvent.value = "fv_vp_container"
        }
    }

    override fun onCleared() {
        repository.car.removeObserver(carObserver)
        super.onCleared()
    }

}
