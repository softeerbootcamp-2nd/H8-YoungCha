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
import com.youngcha.ohmycarset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}