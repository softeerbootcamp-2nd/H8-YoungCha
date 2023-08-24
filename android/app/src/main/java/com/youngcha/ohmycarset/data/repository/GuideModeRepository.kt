package com.youngcha.ohmycarset.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.GuideModeApiService
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.dto.GuideModeDTO
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo

class GuideModeRepository(private val guideModeApiService: GuideModeApiService) {

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> = _car

    fun filterData(guideModeDTO: GuideModeDTO, type: String): List<OptionInfo> {
        return guideModeDTO.data.map { dataItem ->
            val mainImage: String = dataItem.images.find { it.imgType == 0 }?.imgUrl ?: ""
            val subImage: String = dataItem.images.find { it.imgType == 1 }?.imgUrl ?: ""
            val logoImage: String = dataItem.images.find { it.imgType == 2 }?.imgUrl ?: ""
            val feedbackDescription = dataItem.feedbackDescription ?: ""

            OptionInfo(
                id = dataItem.id,
                categoryId = dataItem.categoryId,
                checked = dataItem.checked,
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

    suspend fun fetchAllGuideDataAndSetCar(guideParam: GuideParam, categories: List<Category>) {
        val powerTrain = filterData(
            guideModeApiService.getPowerTrainGuide(
                guideParam.id,
                guideParam.gender,
                guideParam.age,
                guideParam.keyword1Id,
                guideParam.keyword2Id,
                guideParam.keyword3Id
            ), "파워 트레인"
        )
        val drivingSystem = filterData(
            guideModeApiService.getDrivingSystem(
                guideParam.id,
                guideParam.gender,
                guideParam.age,
                guideParam.keyword1Id,
                guideParam.keyword2Id,
                guideParam.keyword3Id
            ), "구동 방식"
        )
        val bodyType = filterData(
            guideModeApiService.getBodyTypeGuide(
                guideParam.id,
                guideParam.gender,
                guideParam.age,
                guideParam.keyword1Id,
                guideParam.keyword2Id,
                guideParam.keyword3Id
            ), "바디 타입"
        )

        val exteriorColor = filterData(
            guideModeApiService.getExteriorColorGuide(
                guideParam.id,
                guideParam.gender,
                guideParam.age,
                guideParam.keyword1Id,
                guideParam.keyword2Id,
                guideParam.keyword3Id
            ), "외장 색상"
        )
        val firstExteriorId = exteriorColor.firstOrNull()?.id
        val interiorColor = if (firstExteriorId != null)
            filterData(
                guideModeApiService.getInteriorColorGuide(
                    guideParam.id,
                    guideParam.gender,
                    guideParam.age,
                    guideParam.keyword1Id,
                    guideParam.keyword2Id,
                    guideParam.keyword3Id, firstExteriorId
                ), "내장 색상"
            )
        else emptyList()

        val wheel = if (firstExteriorId != null)
            filterData(
                guideModeApiService.getWheelGuide(
                    guideParam.id,
                    guideParam.gender,
                    guideParam.age,
                    guideParam.keyword1Id,
                    guideParam.keyword2Id,
                    guideParam.keyword3Id, firstExteriorId
                ), "휠 선택"
            )
        else emptyList()

        val options = createSubOptionMap(
            categories, filterData(
                guideModeApiService.getOptionsGuide(
                    guideParam.id,
                    guideParam.gender,
                    guideParam.age,
                    guideParam.keyword1Id,
                    guideParam.keyword2Id,
                    guideParam.keyword3Id
                ), "옵션 선택"
            )
        )

        val mainOptionsMap = mapOf(
            "파워 트레인" to powerTrain,
            "구동 방식" to drivingSystem,
            "바디 타입" to bodyType,
            "외장 색상" to exteriorColor,
            "내장 색상" to interiorColor,
            "휠 선택" to wheel
        )

        // '옵션 선택'은 별도로 subOptions에 추가
        val subOptionsMap = mapOf(
            "옵션 선택" to options
        )

        val subOptionsList = subOptionsMap.values.toList()

        val updatedCar = Car(
            mainOptions = listOf(mainOptionsMap),
            subOptions = subOptionsList
        )

        _car.value = updatedCar
    }


    fun createSubOptionMap(
        categories: List<Category>,
        options: List<OptionInfo>
    ): Map<String, List<OptionInfo>> {
        // 카테고리 ID를 key로, 카테고리 이름을 value로 갖는 Map 생성
        val categoryIdToNameMap = categories.associateBy({ it.id }, { it.name })

        // OptionInfo List를 카테고리 ID를 기준으로 그룹화
        val groupedOptions = options.groupBy { it.categoryId }

        // 그룹화된 OptionInfo를 카테고리 이름으로 변환
        return groupedOptions.mapKeys { categoryIdToNameMap[it.key] ?: "옵션 선택" }
    }

}