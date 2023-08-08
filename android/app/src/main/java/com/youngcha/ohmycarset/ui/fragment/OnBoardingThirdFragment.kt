package com.youngcha.ohmycarset.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentOnboardingPage3Binding
import com.youngcha.ohmycarset.ui.activity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingThirdFragment : Fragment() {
    private var _binding: FragmentOnboardingPage3Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(activity, MainActivity::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            binding.ivOnboarding.visibility = View.VISIBLE
            binding.ivOnboarding.startAnimation(fadeInAnimation)
        }

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.trimSelectFragment, null, navOptions)
            //startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}