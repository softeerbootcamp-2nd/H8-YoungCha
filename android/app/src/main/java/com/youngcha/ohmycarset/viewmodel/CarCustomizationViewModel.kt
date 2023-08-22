package com.youngcha.ohmycarset.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.enums.AdditionalTab
import com.youngcha.ohmycarset.enums.ImageType
import com.youngcha.ohmycarset.model.car.Car
import com.youngcha.ohmycarset.model.car.ImageInfo
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.util.OPTION_SELECTION
import kotlin.random.Random

class CarCustomizationViewModel : ViewModel() {
    // 자동차 정보 관련 변수들
    // 관련된 변수: selectedCar, currentComponentName, customizedParts
    private val _selectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> = _selectedCar

    private val _currentComponentName = MutableLiveData<String>()
    val currentComponentName: LiveData<String> = _currentComponentName

    private val _customizedParts = MutableLiveData<List<Map<String, List<OptionInfo>>>>()
    val customizedParts: LiveData<List<Map<String, List<OptionInfo>>>> = _customizedParts

    // 선택 모드 관련 변수
    // 관련된 변수: currentType
    private val _currentType = MutableLiveData<String>("SelfMode")
    val currentType: LiveData<String> = _currentType

    // 현재 선택된 옵션
    val currentSelectedOption = MutableLiveData<OptionInfo>()

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

    private val _estimateSubOptions = MutableLiveData<Map<String,List<OptionInfo>>?>()
    val estimateSubOptions: LiveData<Map<String,List<OptionInfo>>?> = _estimateSubOptions

    private val _estimateMainOptions = MutableLiveData<Map<String,List<OptionInfo>>?>()
    val estimateMainOptions: LiveData<Map<String,List<OptionInfo>>?> = _estimateMainOptions

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

    // 초기화 블록
    init {
        loadCarData("팰리세이드")
        _currentComponentName.value = selectedCar.value?.mainOptions?.get(0)?.keys?.first()
        totalPrice.value = 36000000
        prevPrice.value = 36000000
        /*
        addSource 메서드를 사용하여 다른 LiveData (_currentComponentName)의 업데이트를 감지합니다.
        al optionList = ...: _selectedCar.value?.mainOptions?... 이 부분은 _selectedCar의 현재 값을 가져와서 그 안의 mainOptions를 검색하고, 그 안에서 componentName 키를 가진 첫 번째 항목을 찾습니다.

        firstOrNull { it.containsKey(componentName) }: mainOptions 중에서 componentName을 키로 가진 첫 번째 항목을 찾습니다.
        ?.get(componentName): 찾은 항목에서 componentName 키에 대응하는 값을 가져옵니다.
        ?: emptyList(): 만약 앞의 과정에서 값이 null이면, 빈 리스트를 반환합니다.
        currentOptionList.value = optionList: 마지막으로, 찾아낸 optionList 값을 currentOptionList의 값으로 설정합니다.

        간단히 요약하면, _currentComponentName의 값이 변경될 때마다, 해당 값에 해당하는 옵션 리스트를 _selectedCar에서 찾아서 currentOptionList에 설정하는 로직입니다.

        예를 들어, "_currentComponentName"이 "파워 트레인"에서 "바디 타입"으로 변경되면, "바디 타입"에 해당하는 옵션 리스트를 "_selectedCar"에서 찾아서 "currentOptionList"에 업데이트합니다.
         */
        currentOptionList.addSource(_currentComponentName) { componentName ->
            val optionList =
                _selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
                    ?.get(componentName) ?: emptyList()
            currentOptionList.value = optionList
        }

        _currentSubTabs.value = getSubOptionKeys()

        val subOptionKeys = getSubOptionKeys()
        val estimateTabs = mutableListOf<String>()
        estimateTabs.add("전체")
        estimateTabs.addAll(subOptionKeys)
        _currentEstimateSubTabs.value = estimateTabs

    }

    // --- 프래그먼트 초기 시점 함수 ---

    /**
     *  초기 시작 지점
     *  가이드 모드일 경우 부품 미리 생성
     */
    fun initCarCustomizationViewModel(currentType: String, startPoint: String) {
        _currentType.value = currentType
        when (currentType) {
            "GuideMode" -> {
                val lastTab = currentMainTabs.value?.lastOrNull()
                randomizeParts()
                if (startPoint == "start") {
                    currentTabName.value = currentMainTabs.value!![0]
                    setCurrentComponentName(currentTabName.value!!)
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

    fun randomizeParts() {
        val randomizedParts = mutableListOf<Map<String, List<OptionInfo>>>()

        // 주 옵션에서 무작위로 선택
        _selectedCar.value?.mainOptions?.forEach { mainOptionMap ->
            val randomizedMainOptions = mainOptionMap.map { entry ->
                val randomOption = entry.value.random()
                entry.key to listOf(randomOption)
            }.toMap()

            randomizedParts.add(randomizedMainOptions)
        }

        // 부 옵션에서 무작위로 선택
        _selectedCar.value?.subOptions?.forEach { subOptionMap ->
            val randomizedSubOptions = subOptionMap.map { entry ->
                val randomOption = entry.value.random()
                entry.key to listOf(randomOption)
            }.toMap()

            randomizedParts.add(randomizedSubOptions)
        }

        _customizedParts.value = randomizedParts
    }

    fun updateTapPosition(position: Int, tabName: String) {
        _currentTabPosition.value = position
        _currentComponentName.value = tabName

        prevPrice.value = totalPrice.value?.minus(getMyCarTotalPrice())
        totalPrice.value = 36000000
        setCurrentComponentName(tabName)

        if (OPTION_SELECTION == tabName)  handleSubTab()

       // setCurrentComponentName(currentComponentName.value!!)
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
    fun updateCurrentType(currentType: String) {
        _currentType.value = currentType
        _currentTabPosition.value = 0
        currentTabName.value = currentMainTabs.value!![0]
        currentSubTabPosition.value = 0
        totalPrice.value = 36000000
        _customizedParts.value = emptyList()

        setCurrentComponentName(currentTabName.value!!)
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

    /**
     * 현재 컴포넌트 이름 설정
     */
    fun setCurrentComponentName(componentName: String) {
        _currentComponentName.value = componentName
        if (componentName == "견적 내기") {
            totalPrice.value = totalPrice.value?.plus(getMyCarTotalPrice())
            _estimateViewVisible.value = 1
        } else {
            _estimateViewVisible.value = 0
        }

        horizontalButtonVisible.value = 0
        swipeButtonVisible.value = 0

        if (getOptionSize(componentName) <= 2 && componentName != OPTION_SELECTION) {
            // 하단 버튼 visible
            horizontalButtonVisible.value = 1
            alreadySelectedComponent(componentName)
        } else {
            // viewpager visible
            swipeButtonVisible.value = 1
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
        /*
        val data = listOf(
            mapOf("Engine" to listOf(OptionInfo("V6"), OptionInfo("V8"))),
            mapOf("Color" to listOf(OptionInfo("Red"), OptionInfo("Blue"), OptionInfo("Green")))
        )
        _customizedParts.value = data

        _customizedParts의 값을 car 변수에 할당합니다. 위의 예제에서는 data 리스트입니다.
        car 목록에서 "Color"를 키로 가진 첫 번째 항목의 인덱스를 찾습니다. 위의 예제에서는 인덱스 1에 위치하고 있습니다.
        해당 항목을 찾았기 때문에, 그 항목을 myCarToMap 변수에 할당합니다. 따라서 myCarToMap의 값은 mapOf("Color" to listOf(OptionInfo("Red"), OptionInfo("Blue"), OptionInfo("Green")))가 됩니다.
        myCarToMap에서 "Color"에 해당하는 값을 반환합니다. 결과적으로 result의 값은 listOf(OptionInfo("Red"), OptionInfo("Blue"), OptionInfo("Green"))가 됩니다.
        이렇게 함수는 "Color" 탭의 옵션 목록을 반환합니다. 만약 "Color"와 같은 키를 갖는 항목이 _customizedParts에 없다면 함수는 null을 반환합니다.
         */
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

    /**
     * 차량 데이터 로드
     */
    private fun loadCarData(carName: String) {
        _selectedCar.value = createCarData(carName)
        updateTabInfo(_selectedCar.value!!)
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

    fun filterSubOptions(tabName: String) {
        val map: MutableMap<String, List<OptionInfo>> = mutableMapOf()

        if (tabName == "전체") {
            val size = _currentEstimateSubTabs.value?.size

            for (i in 1 until (size ?: 0)) {
                val key = _currentEstimateSubTabs.value?.get(i).toString()
                val subOptionInfoList = _customizedParts.value?.firstOrNull { it.containsKey(key) }?.get(key)
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
            val mainOptionInfoList = _customizedParts.value?.firstOrNull { it.containsKey(key) }?.get(key)
            if (mainOptionInfoList != null) {
                map[key] = mainOptionInfoList
            }
        }
        _estimateMainOptions.value = map
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

        if (getOptionSize(currentComponentName.value!!) <= 2) {
            if (componentOption1Visibility.value == 1) {
                _startAnimationEvent.value = "fv_component_option_1"
            } else if (componentOption2Visibility.value == 1) {
                _startAnimationEvent.value = "fv_component_option_2"
            }
        } else {
            _startAnimationEvent.value = "fv_vp_container"
        }
    }

    // Test Data
    private fun createCarData(carName: String): Car {
        val mainOptions = mapOf(
            "파워 트레인" to listOf(
                OptionInfo(
                    "main",
                    "63%",
                    "디젤 2.2",
                    1480000,
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                ),
                OptionInfo(
                    "main",
                    "72%",
                    "가솔린 3.8",
                    0,
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                )
            ),
            "구동 방식" to listOf(
                OptionInfo("main", "72%", "2WD", 0, ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo(
                    "main",
                    "28%",
                    "4WD",
                    2370000,
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                )
            ),
            "바디 타입" to listOf(
                OptionInfo("main", "85%", "7인승", 0, ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo("main", "15%", "8인승", 0, ImageInfo(ImageType.NONE, 0), emptyList())
            ),
            "외장 색상" to listOf(
                OptionInfo(
                    "color",
                    "50%",
                    "크리미 화이트 펄",
                    1000000,
                    ImageInfo(ImageType.CIRCLE, R.color.black),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "30%",
                    "어비스 플랙펄",
                    0,
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_001),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "25%",
                    "그라파이트 그레이 메탈릭",
                    0,
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_002),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "7%",
                    "쉬머링 실버 메탈릭",
                    0,
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_003),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "3%",
                    "문라이트 블루 펄",
                    0,
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_004),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "2%",
                    "가이아미 브라운 펄",
                    0,
                    ImageInfo(ImageType.CIRCLE, R.color.active_blue),
                    emptyList()
                )
            ),
            "내장 색상" to listOf(
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 천연 (블랙)",
                    0,
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "43%",
                    "쿨그레이",
                    0,
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_02),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "하하하 천연 (블랙)",
                    0,
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "테스트입니다(블랙)",
                    0,
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 천연 123(블랙)",
                    2334560,
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                )
            ),
            "휠 선택" to listOf(
                OptionInfo(
                    "main",
                    "30%",
                    "20인치 알로이 휠 & 타이어",
                    2280000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("스탠다드 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 다크 스퍼터링 휠",
                    4280000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("20대 61%", "여성 65%")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 다12크 스퍼터링 휠",
                    4280000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "28인치 다크 휠",
                    5600000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "32인치 더블 휠",
                    6570000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                )
            )
        )
        val mainOptionImages = mapOf(
            "파워 트레인" to R.drawable.img_test_make_car_01,
            "구동 방식" to R.drawable.img_test_make_car_02,
            "바디 타입" to R.drawable.img_test_make_car_03,
            "외장 색상" to R.drawable.img_test_make_car_04,
            "내장 색상" to R.drawable.img_test_make_car_05,
            "휠 선택" to R.drawable.img_test_make_car_06,
        )

        val subOptions = mapOf(
            "시스템" to listOf(
                OptionInfo(
                    "sub",
                    "60%",
                    "최신 시스템",
                    1880000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인21포테인먼트 시스템")
                ),
                OptionInfo(
                    "sub",
                    "60%",
                    "test123",
                    100000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인포테인먼트 시스템")
                ),
                OptionInfo(
                    "sub",
                    "60%",
                    "tttasdasdjaslkd",
                    18810000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인포테인먼트 시스템")
                )
            ),
            "온도관리" to listOf(
                OptionInfo(
                    "sub",
                    "40%",
                    "자동 온도 조절",
                    980000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                ),
                OptionInfo(
                    "sub",
                    "20%",
                    "자동12 조절",
                    1980000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                ),
                OptionInfo(
                    "sub",
                    "10%",
                    "온도 조절",
                    440000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                )
            ),
            "외부장치" to listOf(
                OptionInfo(
                    "sub",
                    "50%",
                    "외부 디바이스 호환",
                    1280000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                ),
                OptionInfo(
                    "sub",
                    "10%",
                    "soqn 디바이스 호환",
                    120000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                ),
                OptionInfo(
                    "sub",
                    "20%",
                    "내부 디바이스 호환",
                    3200000,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                )
            ),
            "내부장치" to listOf(
                OptionInfo(
                    "sub",
                    "50%",
                    "프리미엄 사운드",
                    677700,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("높은 품질의 사운드 시스템")
                ),
                OptionInfo(
                    "sub",
                    "4%",
                    "사운드",
                    349090,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("높은 품질의 사운드 시스템")
                ),
                OptionInfo(
                    "sub",
                    "1000%",
                    "그냥 사운드",
                    1999999,
                    ImageInfo(ImageType.NONE, 0),
                    listOf("높은 품질의 사운드 시스템")
                )
            )
        )

        val subOptionImage = mapOf(
            "옵션 선택" to R.drawable.img_test_make_car_07
        )

        return Car(
            "팰리세이드",
            "SUV",
            listOf(mainOptions),
            listOf(mainOptionImages),
            listOf(subOptions),
            subOptionImage
        )
    }

}
