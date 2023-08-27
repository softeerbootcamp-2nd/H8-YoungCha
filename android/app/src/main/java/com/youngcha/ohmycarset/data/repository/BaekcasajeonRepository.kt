package com.youngcha.ohmycarset.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.BaekcasajeonApiService
import com.youngcha.ohmycarset.data.dto.DataXXX
import com.youngcha.ohmycarset.data.model.Baekcasajeon

class BaekcasajeonRepository(private val baekcasajeonApiService: BaekcasajeonApiService) {

    private val _baekcasajeonSuccess = MutableLiveData<Boolean>()
    val baekcasajeonSuccess: LiveData<Boolean> = _baekcasajeonSuccess

    private val _baekcasajeon = MutableLiveData<List<Baekcasajeon>>()
    val baekcasajeon: LiveData<List<Baekcasajeon>> = _baekcasajeon

    suspend fun getBaekcasajeon() {
        try {
            val response = baekcasajeonApiService.getBaekcasajeon()
            if (response.message == "success") {
                _baekcasajeonSuccess.postValue(true)
                val baekcasajeons = response.data.map { mapToBaekcasajeon(it)}
                _baekcasajeon.postValue(baekcasajeons) // 추가
            } else {
               _baekcasajeonSuccess.postValue(false)
            }
        } catch (e: Exception) {
            _baekcasajeonSuccess.postValue(false)
        }
    }

    fun mapToBaekcasajeon(data: DataXXX) : Baekcasajeon {
        return Baekcasajeon(data.word, data.description, data.imgUrl)
    }
}