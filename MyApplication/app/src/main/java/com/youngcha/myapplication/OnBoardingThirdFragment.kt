package com.hr.teamproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.hr.teamproject.databinding.FragmentOnboardingPage3Binding

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
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            binding.ivOnboarding.startAnimation(fadeInAnimation)
            binding.ivOnboarding.visibility = View.VISIBLE
        }, 500)

        binding.btnNext.setOnClickListener {
            startActivity(intent)
        }
    }
}