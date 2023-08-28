package com.youngcha.ohmycarset.util

import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.dto.GuideModeDTO
import com.youngcha.ohmycarset.data.model.car.OptionInfo

suspend fun <T> retryApiCall(apiCall: suspend () -> T): T? {
    var retryCount = 0
    val maxRetry = 1
    while (retryCount <= maxRetry) {
        try {
            return apiCall()
        } catch (e: Exception) {
            if (retryCount >= maxRetry) {
                throw e
            }
            retryCount++
        }
    }
    return null
}

fun selfModeFilterData(componentDTO: ComponentDTO, type: String): List<OptionInfo> {
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

fun guideModeFilterData(guideModeDTO: GuideModeDTO, type: String): List<OptionInfo> {
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