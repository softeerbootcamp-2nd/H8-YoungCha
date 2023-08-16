package com.youngcha.ohmycarset.viewmodel

import android.util.Log
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

class CarCustomizationViewModel : ViewModel() {

    // 사용자가 선택한 자동차
    private val _selectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> = _selectedCar

    // 현재 선택한 자동차 이름 == _selectedCar.value?.name
    private val _currentComponentName = MutableLiveData<String>()
    val currentComponentName: LiveData<String> = _currentComponentName

    // 내가 커스터마이징 하고 있는 자동차
    private val _customizedParts = MutableLiveData<List<Map<String, List<OptionInfo>>>>()
    val customizedParts: LiveData<List<Map<String, List<OptionInfo>>>> = _customizedParts

    private val _currentType = MutableLiveData<String>("SelfMode")
    val currentType: LiveData<String> = _currentType

    /*
    파워트레인, 구동 방식, 바디 타입등 선택할 수 있는 옵션이 2개이하라면 Horizontal Two Button 으로 선택 가능
    그 중 componentOption1Visibility는 왼쪽 버튼이 선택 됐을 경우 버튼 layout 변경 (ex: background....)
         componentOption2Visibility는 오른쪽 버튼 선택 됐을 경우 처리

    옵션이 2개 이상이라면 스와이프 형식으로 옵션 선택 ViewPager사용
    tlOptionVisibility -> ViewPager2 가시성 제어
    만약 tlOptionVisibility가 1이라면 ViewPager보이고 2개 버튼 표시되는 View 가리기
    */
    val componentOption1Visibility = MutableLiveData<Int>()
    val componentOption2Visibility = MutableLiveData<Int>()

    val currentOptionList = MediatorLiveData<List<OptionInfo>>()

    val horizontalButtonVisible = MutableLiveData<Int>(0) // 가로 버튼 visible
    val swipeButtonVisible = MutableLiveData<Int>(0) // viewpager visible
    val subOptionButtonVisible = MutableLiveData<Int>(1) // subOption: 1이면 mainImage보이게 0이면 안보이게
    val subOptionViewTypeChangeButton =
        MutableLiveData<Int>(0) // sub option viewpager <-> recyclerview 전환 이미지
    val subOptionViewType = MutableLiveData<Int>(0)


    // --------------
    private val _mainOptionsTabs = MutableLiveData<List<String>>()
    val mainOptionsTabs: LiveData<List<String>> = _mainOptionsTabs

    private val _additionalTabs = MutableLiveData<List<String>>()
    val additionalTabs: LiveData<List<String>> = _additionalTabs
    // --------------

    // init block
    init {
        loadCarData("팰리세이드")
        _currentComponentName.value = selectedCar.value?.mainOptions?.get(0)?.keys?.first()
        currentOptionList.addSource(_currentComponentName) { componentName ->
            val optionList =
                _selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
                    ?.get(componentName) ?: emptyList()
            currentOptionList.value = optionList
        }
    }

    fun updateCurrentType(currentType: String) {
        _currentType.value = currentType
        resetCustomizedParts()
    }

    fun resetCustomizedParts() {
        _customizedParts.value = emptyList()
    }


    fun updateTabInformation(car: Car) {
        val mainOptions = car.mainOptions[0].keys.mapIndexed { index, carOptionKey ->
            "${String.format("%02d", index + 1)} $carOptionKey"
        }
        _mainOptionsTabs.value = mainOptions

        val additionalTabsList = AdditionalTab.values().mapIndexed { index, additionalTab ->
            val tabIndex = car.mainOptions[0].keys.size + index + 1
            "${String.format("%02d", tabIndex)} ${additionalTab.displayName}"
        }
        _additionalTabs.value = additionalTabsList
    }

    fun setCurrentComponentName(componentName: String) {
        _currentComponentName.value = componentName

        horizontalButtonVisible.value = 0
        swipeButtonVisible.value = 0

        if (getOptionSize(componentName) <= 2 && componentName != OPTION_SELECTION) {
            horizontalButtonVisible.value = 1
            alreadySelectedComponent(componentName)
        } else {
            swipeButtonVisible.value = 1
        }
    }

    fun getSubOptionKeys(): List<String> {
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
            var existingOptions = existingComponent[keyName]?.toMutableList() ?: mutableListOf()
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
        //_myCar.postValue(updatedList)
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
        val car = _customizedParts.value

        val index = car?.indexOfFirst { it.containsKey(tabName) }

        if (index != -1) {
            val myCarToMap = index?.let { car?.get(it) }
            return myCarToMap?.get(tabName)
        }
        return null
    }

    fun alreadySelectedComponent(componentName: String) {
        val car = _customizedParts.value ?: listOf()
        val index = car.indexOfFirst { it.containsKey(componentName) }
        setComponentOptionVisibility(1, 0)
        if (index != -1) {
            // 이미 선택된 값들이 있다면
            val myCarToList = car[index]
            val myCarToMap = myCarToList[componentName] // 선택된 값

            val option = getOption(componentName, 0) ?: return // 0번 인덱스 옵션 가져오기
            if (option == myCarToMap?.get(0)) {
                setComponentOptionVisibility(1, 0)
            } else {
                setComponentOptionVisibility(0, 1)
            }
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

    fun setComponentOptionVisibility(option1Visible: Int, option2Visible: Int) {
        componentOption1Visibility.value = option1Visible
        componentOption2Visibility.value = option2Visible
    }

    fun onSubOptionChanged(view: View) {
        subOptionButtonVisible.value = if (subOptionButtonVisible.value == 1) 0 else 1
        subOptionViewType.value = if (subOptionViewType.value == 1) 0 else 1
    }

    fun setOptionSelectionTabValues() {
        subOptionViewTypeChangeButton.value = 1
        subOptionButtonVisible.value = 1
    }

    fun getOptionsForComponent(componentName: String): List<OptionInfo>? {
        return selectedCar.value?.mainOptions?.firstOrNull { it.containsKey(componentName) }
            ?.get(componentName)
    }

    fun getOptionSize(componentName: String): Int {
        return getOptionsForComponent(componentName)?.size ?: 0
    }

    fun getOption(componentName: String, index: Int): OptionInfo? {
        return getOptionsForComponent(componentName)?.getOrNull(index)
    }

    fun setDefaultTabValues() {
        subOptionViewTypeChangeButton.value = 0
        subOptionButtonVisible.value = 1
    }


    fun loadCarData(carName: String) {
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