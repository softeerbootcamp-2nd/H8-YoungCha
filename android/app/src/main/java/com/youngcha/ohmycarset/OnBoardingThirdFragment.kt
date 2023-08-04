package com.youngcha.ohmycarset

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.youngcha.ohmycarset.databinding.FragmentOnboardingPage3Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingThirdFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingPage3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(activity, MainActivity::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            binding.ivOnboarding.startAnimation(fadeInAnimation)
            binding.ivOnboarding.visibility = View.VISIBLE
        }

        binding.btnNext.setOnClickListener {
            startActivity(intent)
        }
    }
}