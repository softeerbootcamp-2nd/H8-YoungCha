package com.youngcha.ohmycarset.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.youngcha.ohmycarset.data.api.RetrofitClient
import com.youngcha.ohmycarset.data.repository.BaekcasajeonRepository
import com.youngcha.ohmycarset.databinding.ActivityMainBinding
import com.youngcha.ohmycarset.viewmodel.BaekcasajeonViewModel
import com.youngcha.ohmycarset.viewmodel.factory.BaekcasajeonFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val baekcasajeonRepository by lazy { BaekcasajeonRepository(RetrofitClient.baekcasajeonApi) }
    private val baekcasajeonViewModel: BaekcasajeonViewModel by viewModels {
        BaekcasajeonFactory(baekcasajeonRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}