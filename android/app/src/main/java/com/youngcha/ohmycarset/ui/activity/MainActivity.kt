package com.youngcha.ohmycarset.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.data.api.CategoriesApiService
import com.youngcha.ohmycarset.databinding.ActivityMainBinding
import com.youngcha.ohmycarset.data.api.RetrofitClient
import com.youngcha.ohmycarset.data.api.SelfModeApiService
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.dto.CategoryDTO
import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import com.youngcha.ohmycarset.enums.AdditionalTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val apiService = RetrofitClient.apiService
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // API 요청
        mainScope.launch {
            try {
                val response = apiService.getCarMakeDictionary()
                if (response.isSuccessful) {
                    Log.d("API", "success")
//                    val dictionary = response.body()
//                    if (dictionary != null) {
//
//                    }
                }
                val responseMain = apiService.getMainPageData(2)
                if(responseMain.isSuccessful)
                {
                    Log.d("API", "success")
                    val main = responseMain.body()
                    Log.d("BODY", main.toString())
                }
            } catch (e: Exception) {
                // 처리
            }
        }
    }
}