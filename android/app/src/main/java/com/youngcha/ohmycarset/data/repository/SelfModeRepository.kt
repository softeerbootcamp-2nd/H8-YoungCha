package com.youngcha.ohmycarset.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.SelfModeApiService
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo
import com.youngcha.ohmycarset.util.retryApiCall
import com.youngcha.ohmycarset.util.selfModeFilterData

class SelfModeRepository(private val selfModeApiService: SelfModeApiService) {

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> = _car

    suspend fun setCarComponent(carMakeId: Int, componentType: String, categories: List<Category>) {
        val currentCar = _car.value ?: Car()
        if (currentCar.mainOptions.any { it.containsKey(componentType) }) return

        val newComponent: Map<String, List<OptionInfo>>

        when (componentType) {
            "파워 트레인" -> newComponent = mapOf(componentType to selfModeFilterData(retryApiCall { selfModeApiService.getPowerTrain(carMakeId) } ?: return, "main"))
            "구동 방식" -> newComponent = mapOf(componentType to selfModeFilterData(retryApiCall { selfModeApiService.getDrivingSystem(carMakeId) } ?: return, "main"))
            "바디 타입" -> newComponent = mapOf(componentType to selfModeFilterData(retryApiCall { selfModeApiService.getBodyType(carMakeId) } ?: return, "main"))
            "외장 색상" -> newComponent = mapOf(componentType to selfModeFilterData(retryApiCall { selfModeApiService.getExteriorColor(carMakeId) } ?: return, "color"))
            "휠 선택" -> newComponent = mapOf(componentType to selfModeFilterData(retryApiCall { selfModeApiService.getWheel(carMakeId) } ?: return, "main"))
            "옵션 선택" -> newComponent = createSubOptionMap(categories, selfModeFilterData(retryApiCall { selfModeApiService.getOptions(carMakeId) } ?: return, "sub"))
            else -> throw IllegalArgumentException("Unsupported component type: $componentType")
        }

        val updatedCar = when (componentType) {
            "옵션 선택" -> currentCar.copy(subOptions = currentCar.subOptions + newComponent)
            else -> currentCar.copy(mainOptions = currentCar.mainOptions + newComponent)
        }
        _car.value = updatedCar
    }

    suspend fun setInteriorColor(carMakeId: Int, exteriorColorId: Int) {
        val currentCar = _car.value ?: Car()
        val updatedComponent = mapOf("내장 색상" to selfModeFilterData(retryApiCall { selfModeApiService.getInteriorColor(carMakeId, exteriorColorId) } ?: return, "color"))

        val containsInteriorColor = currentCar.mainOptions.any { it.keys.contains("내장 색상") }

        val newMainOptions = if (containsInteriorColor) {
            currentCar.mainOptions.map {
                if (it.keys.contains("내장 색상")) updatedComponent else it
            }
        } else {
            ArrayList(currentCar.mainOptions).apply { add(updatedComponent) }
        }

        val updatedCar = currentCar.copy(mainOptions = newMainOptions)
        _car.value = updatedCar
    }

    fun createSubOptionMap(categories: List<Category>, options: List<OptionInfo>): Map<String, List<OptionInfo>> {
        // 카테고리 ID를 key로, 카테고리 이름을 value로 갖는 Map 생성
        val categoryIdToNameMap = categories.associateBy({ it.id }, { it.name })

        // OptionInfo List를 카테고리 ID를 기준으로 그룹화
        val groupedOptions = options.groupBy { it.categoryId }

        // 그룹화된 OptionInfo를 카테고리 이름으로 변환
        return groupedOptions.mapKeys { categoryIdToNameMap[it.key] ?: "옵션 선택" }
    }
}