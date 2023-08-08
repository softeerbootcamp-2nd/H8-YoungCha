package com.youngcha.ohmycarset.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youngcha.ohmycarset.databinding.OnboardingActivityBinding

internal class OnBoardingActivity : AppCompatActivity() {
private lateinit var binding: OnboardingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MainActivity::class.java)

        binding.btnSkip.setOnClickListener{
            startActivity(intent)
        }
    }
}