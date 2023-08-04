package com.youngcha.ohmycarset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.databinding.FragmentOnboardingPage1Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingFirstFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingPage1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            binding.ivOnboarding.startAnimation(fadeInAnimation)
            binding.ivOnboarding.visibility = View.VISIBLE
        }

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.onboardingPage2, null,navOptions)
        }
    }
}
