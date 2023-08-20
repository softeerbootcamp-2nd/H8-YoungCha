package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentOnboardingBinding
import com.youngcha.ohmycarset.util.fadeIn
import com.youngcha.ohmycarset.util.fadeOut
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBoardingStepChange()

    }

    private fun handleOnBoardingStepChange() {
        binding.layoutOnboardingPage1.btnNext.setOnClickListener {
            binding.layoutOnboardingPage1.clRootView.fadeOut()
            binding.layoutOnboardingPage2.clRootView.fadeIn()
            imageAnimation(2)
        }

        binding.layoutOnboardingPage2.btnNext.setOnClickListener {
            binding.layoutOnboardingPage2.clRootView.fadeOut()
            binding.layoutOnboardingPage3.clRootView.fadeIn()
            imageAnimation(3)
        }

        binding.layoutOnboardingPage3.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_trimSelectFragment)
        }

        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_trimSelectFragment)
        }
    }

    private fun imageAnimation(step: Int) {
        when (step) {
            2 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                    binding.layoutOnboardingPage2.ivOnboarding.visibility = View.VISIBLE
                    binding.layoutOnboardingPage2.ivOnboarding.startAnimation(fadeInAnimation)
                }
            }

            3 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                    binding.layoutOnboardingPage3.ivOnboarding.visibility = View.VISIBLE
                    binding.layoutOnboardingPage3.ivOnboarding.startAnimation(fadeInAnimation)
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}