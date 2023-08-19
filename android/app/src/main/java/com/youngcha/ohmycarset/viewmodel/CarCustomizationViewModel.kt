package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.enums.AdditionalTab
import com.youngcha.ohmycarset.enums.ImageType
import com.youngcha.ohmycarset.model.car.Car
import com.youngcha.ohmycarset.model.car.ImageInfo
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.util.OPTION_SELECTION

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
    private val _currentTabPosition =  MutableLiveData<Int>(0)
    val currentTabPosition: LiveData<Int> = _currentTabPosition
    val currentSubTabPosition = MutableLiveData<Int>(0)
    val currentMainTabs = MutableLiveData<List<String>>()
    private val _currentSubTabs = MutableLiveData<List<String>>()
    val currentSubTabs: LiveData<List<String>> = _currentSubTabs

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

    // init block
    init {
        loadCarData("팰리세이드")
        _currentComponentName.value = selectedCar.value?.mainOptions?.get(0)?.keys?.first()

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
    }

    fun updateEstimateColorButton(view:View) {
        exteriorButtonChange.value = if(exteriorButtonChange.value==1) 0 else 1
    }
    fun updateCurrentType(currentType: String) {
        _currentType.value = currentType
        _currentTabPosition.value = 0
        currentTabName.value = currentMainTabs.value!![0]
        currentSubTabPosition.value = 0
        _customizedParts.value = emptyList()
        setCurrentComponentName(currentTabName.value!!)
    }

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

    fun handleTabChange(increment: Int) {
        val currentTabIndex = currentTabPosition.value ?: 0
        val nextTabIndex = currentTabIndex + increment

        // 범위 확인
        if (nextTabIndex in 0 until currentMainTabs.value!!.size) {
            // 탭 변경 로직
            val tabName = currentMainTabs.value!![nextTabIndex]
            setCurrentComponentName(tabName)
            _currentTabPosition.value  = nextTabIndex
            when (tabName) {
                OPTION_SELECTION -> handleSubTab()
                else -> handleMainTab()
            }
        }
    }

    // 메인 탭이 선택되었을 때 처리.
    private fun handleMainTab() {
        setDefaultTabValues()
    }

    // 선택 옵션 탭이 선택되었을 때 처리.
    private fun handleSubTab() {
        updateDataContainer()
        setOptionSelectionTabValues()
    }

    fun setCurrentComponentName(componentName: String) {
        _currentComponentName.value = componentName

        if (componentName == "견적 내기") {
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

    private fun getSubOptionKeys(): List<String> {
        val keys = mutableListOf<String>()
        val car = _selectedCar.value
        car?.subOptions?.forEach { map ->
            keys.addAll(map.keys)
        }
        return keys
    }

    fun getOptionInfoByKey(key: String): List<OptionInfo>? {
        val car = _selectedCar.value
        car?.subOptions?.forEach { map ->
            map[key]?.let {
                return it
            }
        }
        return null
    }

    fun addCarComponents(
        componentName: String,
        option: OptionInfo,
        subOptionName: String? = null
    ) {
        val updatedList = _customizedParts.value?.toMutableList() ?: mutableListOf()

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
        _customizedParts.value = updatedList
    }

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
            val myCarToMap = index?.let { car?.get(it) }
            return myCarToMap?.get(tabName)
        }
        return null
    }

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

    fun onComponentOption1Selected() {
        val componentName = _currentComponentName.value ?: return
        val option = getOption(componentName, 0) ?: return // 0번 인덱스 옵션 가져오기
        addCarComponents(componentName, option)
        setComponentOptionVisibility(1, 0)
    }

    fun onComponentOption2Selected() {
        val componentName = _currentComponentName.value ?: return
        val option = getOption(componentName, 1) ?: return // 1번 인덱스 옵션 가져오기
        addCarComponents(componentName, option)
        setComponentOptionVisibility(0, 1)

    }

    private fun setComponentOptionVisibility(option1Visible: Int, option2Visible: Int) {
        componentOption1Visibility.value = option1Visible
        componentOption2Visibility.value = option2Visible
    }

    fun onSubOptionChanged(view: View) {
        subOptionButtonVisible.value = if (subOptionButtonVisible.value == 1) 0 else 1
        subOptionViewType.value = if (subOptionViewType.value == 1) 0 else 1
    }

    private fun setOptionSelectionTabValues() {
        subOptionViewTypeChangeButton.value = 1
        subOptionButtonVisible.value = 1
    }

    private fun getOptionsForComponent(componentName: String): List<OptionInfo>? {
        return selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
            ?.get(componentName)
    }

    private fun getOptionSize(componentName: String): Int {
        return getOptionsForComponent(componentName)?.size ?: 0
    }

    fun getOption(componentName: String, index: Int): OptionInfo? {
        return getOptionsForComponent(componentName)?.getOrNull(index)
    }

    private fun setDefaultTabValues() {
        subOptionViewTypeChangeButton.value = 0 // viewpager <-> recyclerview
        subOptionButtonVisible.value = 1 // main Image visible
    }

    // NEW
    fun updateDataContainer() {
        currentTabName.value = _currentSubTabs.value?.get(currentSubTabPosition.value!!)
        _displayOnRecyclerViewAndViewPager.value = if (subOptionButtonVisible.value == 0) 0 else 1
    }

    private fun loadCarData(carName: String) {
        _selectedCar.value = createCarData(carName)
    }

    // Test Data
    private fun createCarData(carName: String): Car {
        val mainOptions = mapOf(
            "파워 트레인" to listOf(
                OptionInfo(
                    "main",
                    "63%",
                    "디젤 2.2",
                    "+ 1,480,000원",
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                ),
                OptionInfo(
                    "main",
                    "72%",
                    "가솔린 3.8",
                    "+ 0원",
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                )
            ),
            "구동 방식" to listOf(
                OptionInfo("main", "72%", "2WD", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo(
                    "main",
                    "28%",
                    "4WD",
                    "+ 2,370,000원",
                    ImageInfo(ImageType.NONE, 0),
                    emptyList()
                )
            ),
            "바디 타입" to listOf(
                OptionInfo("main", "85%", "7인승", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo("main", "15%", "8인승", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList())
            ),
            "외장 색상" to listOf(
                OptionInfo(
                    "color",
                    "50%",
                    "크리미 화이트 펄",
                    "+ 100,000원",
                    ImageInfo(ImageType.CIRCLE, R.color.black),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "30%",
                    "어비스 플랙펄",
                    "+ 0",
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_001),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "25%",
                    "그라파이트 그레이 메탈릭",
                    "+ 0",
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_002),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "7%",
                    "쉬머링 실버 메탈릭",
                    "+ 0",
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_003),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "3%",
                    "문라이트 블루 펄",
                    "+ 0",
                    ImageInfo(ImageType.CIRCLE, R.color.cool_grey_004),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "2%",
                    "가이아ㅁㄴㅇ 브라운 펄",
                    "+ 0",
                    ImageInfo(ImageType.CIRCLE, R.color.active_blue),
                    emptyList()
                )
            ),
            "내장 색상" to listOf(
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 천연 (블랙)",
                    "+ 0원",
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "43%",
                    "쿨그레이",
                    "+ 0원",
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_02),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 ㅋㄴㅇ천연 (블랙)",
                    "+ 0원",
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 천연ㅁㄴㅇ (블랙)",
                    "+ 0원",
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                ),
                OptionInfo(
                    "color",
                    "57%",
                    "퀄팅 천연 123(블랙)",
                    "+ 0원",
                    ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01),
                    emptyList()
                )
            ),
            "휠 선택" to listOf(
                OptionInfo(
                    "main",
                    "30%",
                    "20인치 알로이 휠 & 타이어",
                    "+ 2,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("스탠다드 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 다크 스퍼터링 휠",
                    "+ 4,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 다12크 스퍼터링 휠",
                    "+ 4,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 다크 ㅋㅌㅇㄷ스퍼터링 휠",
                    "+ 4,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("프리미엄 선택")
                ),
                OptionInfo(
                    "main",
                    "20%",
                    "20인치 ㅁㄴ다크 스퍼터링 휠",
                    "+ 4,28ㅁㄴㅇ0,000원",
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
                    "+ 1,880,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인21포테인먼트 시스템")
                ),
                OptionInfo(
                    "sub",
                    "60%",
                    "test123",
                    "+ 10,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인포테인먼트 시스템")
                ),
                OptionInfo(
                    "sub",
                    "60%",
                    "tttasdasdjaslkd",
                    "+ 1,8810,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("최신 인포테인먼트 시스템")
                )
            ),
            "온도관리" to listOf(
                OptionInfo(
                    "sub",
                    "40%",
                    "자동 온도 조절",
                    "+ 980,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                ),
                OptionInfo(
                    "sub",
                    "20%",
                    "자동12 조절",
                    "+ 980,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                ),
                OptionInfo(
                    "sub",
                    "10%",
                    "온도 조절",
                    "+ 980,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("자동 온도 조절 기능")
                )
            ),
            "외부장치" to listOf(
                OptionInfo(
                    "sub",
                    "50%",
                    "외부 디바이스 호환",
                    "+ 1,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                ),
                OptionInfo(
                    "sub",
                    "10%",
                    "soqn 디바이스 호환",
                    "+ 1,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                ),
                OptionInfo(
                    "sub",
                    "20%",
                    "내부 디바이스 호환",
                    "+ 1,280,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("USB, Bluetooth 연결 가능")
                )
            ),
            "내부장치" to listOf(
                OptionInfo(
                    "sub",
                    "50%",
                    "프리미엄 사운드",
                    "+ 780,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("높은 품질의 사운드 시스템")
                ),
                OptionInfo(
                    "sub",
                    "4%",
                    "사운드",
                    "+ 780,000원",
                    ImageInfo(ImageType.NONE, 0),
                    listOf("높은 품질의 사운드 시스템")
                ),
                OptionInfo(
                    "sub",
                    "1000%",
                    "그냥 사운드",
                    "+ 780,000원",
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