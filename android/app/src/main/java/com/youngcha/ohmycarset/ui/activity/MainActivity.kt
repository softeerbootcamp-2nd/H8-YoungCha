package com.youngcha.ohmycarset.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.youngcha.ohmycarset.databinding.ActivityMainBinding
import com.youngcha.ohmycarset.repository.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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