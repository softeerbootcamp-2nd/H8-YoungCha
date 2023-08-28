package com.youngcha.ohmycarset.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.SelfModeApiService
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.dto.CategoryDTO
import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo

class SelfModeRepository(private val selfModeApiService: SelfModeApiService) {

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> = _car

    suspend fun <T> retryApiCall(block: suspend () -> T): T? {
        var result: T? = null
        val maxRetries = 2
        var currentAttempt = 0

        while (currentAttempt < maxRetries) {
            try {
                result = block()
                break
            } catch (e: Exception) {
                if (currentAttempt == maxRetries - 1) {
                    throw e
                }
            }
            currentAttempt++
        }

        return result
    }

    fun filterData(componentDTO: ComponentDTO, type: String): List<OptionInfo> {
        return componentDTO.data.map { dataItem ->
            val mainImage: String = dataItem.images.find { it.imgType == 0 }?.imgUrl ?: ""
            val subImage: String = dataItem.images.find { it.imgType == 1 }?.imgUrl ?: ""
            val logoImage: String = dataItem.images.find { it.imgType == 2 }?.imgUrl ?: ""
            val feedbackDescription = dataItem.feedbackDescription ?: ""

            OptionInfo(
                id = dataItem.id,
                categoryId = dataItem.categoryId,
                checked = false,
                optionType = type,
                rate = dataItem.rate.toString(),
                name = dataItem.name,
                price = dataItem.price,
                feedbackTitle = dataItem.feedbackTitle,
                feedbackDescription = feedbackDescription,
                mainImage = mainImage,
                subImage = subImage,
                logoImage = logoImage,
                detail = dataItem.details,
                guideModeDescription = null
            )
        }
    }

    suspend fun setCarComponent(carMakeId: Int, componentType: String, categories: List<Category>) {
        val currentCar = _car.value ?: Car()
        if (currentCar.mainOptions.any { it.containsKey(componentType) }) return

        val newComponent: Map<String, List<OptionInfo>>

        when (componentType) {
            "파워 트레인" -> newComponent = mapOf(componentType to filterData(retryApiCall { selfModeApiService.getPowerTrain(carMakeId) } ?: return, "main"))
            "구동 방식" -> newComponent = mapOf(componentType to filterData(retryApiCall { selfModeApiService.getDrivingSystem(carMakeId) } ?: return, "main"))
            "바디 타입" -> newComponent = mapOf(componentType to filterData(retryApiCall { selfModeApiService.getBodyType(carMakeId) } ?: return, "main"))
            "외장 색상" -> newComponent = mapOf(componentType to filterData(retryApiCall { selfModeApiService.getExteriorColor(carMakeId) } ?: return, "color"))
            "휠 선택" -> newComponent = mapOf(componentType to filterData(retryApiCall { selfModeApiService.getWheel(carMakeId) } ?: return, "main"))
            "옵션 선택" -> newComponent = createSubOptionMap(categories, filterData(retryApiCall { selfModeApiService.getOptions(carMakeId) } ?: return, "sub"))
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
        val updatedComponent = mapOf("내장 색상" to filterData(retryApiCall { selfModeApiService.getInteriorColor(carMakeId, exteriorColorId) } ?: return, "color"))

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