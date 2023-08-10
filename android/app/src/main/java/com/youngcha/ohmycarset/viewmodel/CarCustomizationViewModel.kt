package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.enums.CarOption
import com.youngcha.ohmycarset.enums.ImageType
import com.youngcha.ohmycarset.enums.OptionSelection
import com.youngcha.ohmycarset.model.car.Car
import com.youngcha.ohmycarset.model.car.ImageInfo
import com.youngcha.ohmycarset.model.car.OptionInfo

class CarCustomizationViewModel: ViewModel() {
    private val _selectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> = _selectedCar

    fun loadCarData(carName: String) {
        _selectedCar.value = createCarData(carName)
    }

    // Test Data
    private fun createCarData(carName: String): Car {
        val mainOptions = mapOf(
            CarOption.POWER_TRAIN to listOf(
                OptionInfo("63%", "디젤 2.2", "+ 1,480,000원", ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo("72%", "가솔린 3.8", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList())
            ),
            CarOption.DRIVE_TYPE to listOf(
                OptionInfo("72%", "2WD", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo("28%", "4WD", "+ 2,370,000원", ImageInfo(ImageType.NONE, 0), emptyList())
            ),
            CarOption.BODY_TYPE to listOf(
                OptionInfo("85%", "7인승", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList()),
                OptionInfo("15%", "8인승", "+ 0원", ImageInfo(ImageType.NONE, 0), emptyList())
            ),
            CarOption.EXTERIOR_COLOR to listOf(
                OptionInfo("50%", "크리미 화이트 펄", "+ 100,000원", ImageInfo(ImageType.CIRCLE, R.color.black), emptyList()),
                OptionInfo("30%", "어비스 플랙펄", "+ 0", ImageInfo(ImageType.CIRCLE, R.color.cool_grey_001), emptyList()),
                OptionInfo("25%", "그라파이트 그레이 메탈릭", "+ 0", ImageInfo(ImageType.CIRCLE, R.color.cool_grey_002), emptyList()),
                OptionInfo("7%", "쉬머링 실버 메탈릭", "+ 0", ImageInfo(ImageType.CIRCLE, R.color.cool_grey_003), emptyList()),
                OptionInfo("3%", "문라이트 블루 펄", "+ 0", ImageInfo(ImageType.CIRCLE, R.color.cool_grey_004), emptyList()),
                OptionInfo("2%", "가이아 브라운 펄", "+ 0", ImageInfo(ImageType.CIRCLE, R.color.active_blue), emptyList())
            ),
            CarOption.INTERIOR_COLOR to listOf(
                OptionInfo("57%", "퀄팅 천연 (블랙)", "+ 0원", ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_01), emptyList()),
                OptionInfo("43%", "쿨그레이", "+ 0원", ImageInfo(ImageType.RECTANGLE, R.drawable.img_test_make_car_option_02), emptyList())
            ),
            CarOption.WHEEL_SELECTION to listOf(
                OptionInfo("30%", "20인치 알로이 휠 & 타이어", "+ 2,280,000원", ImageInfo(ImageType.NONE, 0), listOf("스탠다드 선택")),
                OptionInfo("20%", "20인치 다크 스퍼터링 휠", "+ 4,280,000원", ImageInfo(ImageType.NONE, 0), listOf("프리미엄 선택"))
            )
        )
        val mainOptionImages = mapOf(
            CarOption.POWER_TRAIN to R.drawable.img_test_make_car_01,
            CarOption.DRIVE_TYPE to R.drawable.img_test_make_car_02,
            CarOption.BODY_TYPE to R.drawable.img_test_make_car_03,
            CarOption.EXTERIOR_COLOR to R.drawable.img_test_make_car_04,
            CarOption.INTERIOR_COLOR to R.drawable.img_test_make_car_05,
            CarOption.WHEEL_SELECTION to R.drawable.img_test_make_car_06,
        )

        val subOptions = mapOf(
            OptionSelection.SYSTEM to listOf(
                OptionInfo("60%", "최신 시스템", "+ 1,880,000원", ImageInfo(ImageType.NONE, 0), listOf("최신 인포테인먼트 시스템"))
            ),
            OptionSelection.TEMPERATURE_MANAGEMENT to listOf(
                OptionInfo("40%", "자동 온도 조절", "+ 980,000원", ImageInfo(ImageType.NONE, 0), listOf("자동 온도 조절 기능"))
            ),
            OptionSelection.EXTERNAL_DEVICES to listOf(
                OptionInfo("50%", "외부 디바이스 호환", "+ 1,280,000원", ImageInfo(ImageType.NONE, 0), listOf("USB, Bluetooth 연결 가능"))
            ),
            OptionSelection.INTERNAL_DEVICES to listOf(
                OptionInfo("50%", "프리미엄 사운드", "+ 780,000원", ImageInfo(ImageType.NONE, 0), listOf("높은 품질의 사운드 시스템"))
            )
        )

        val subOptionImage = mapOf(
            CarOption.OPTION_SELECTION to R.drawable.img_test_make_car_07
        )

        return Car("팰리세이드", "SUV", listOf(mainOptions), listOf(mainOptionImages), listOf(subOptions), subOptionImage)
    }
}