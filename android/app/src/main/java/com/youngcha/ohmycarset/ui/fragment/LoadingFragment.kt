package com.youngcha.ohmycarset.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngcha.ohmycarset.databinding.FragmentLoadingBinding
import android.animation.ValueAnimator
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.util.AnimationUtils
import kotlinx.coroutines.*

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    private val imageResources = listOf(R.drawable.ic_change, R.drawable.ic_model_change)
    private var currentIndex = 0
    private var imageAnimationCoroutine: Job? = null
    private var imageAndTextAnimationCoroutine: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
        setupAnimations()
    }

    private fun setupClickListener() {
        binding.layoutEstimateReady.btnNext.setOnClickListener {
            var bundle: Bundle?
            bundle = Bundle().apply {
                putString("mode", "GuideMode")
            }
            findNavController().navigate(
                R.id.action_loadingFragment_to_makeCarFragment,
                bundle
            )

        }

        binding.layoutEstimateReady.btnSkip.setOnClickListener {

        }

    }

    private fun setupAnimations() {
        startProgressAnimation()
        startImageChangeAnimation()
        startImageAndTextAnimation()
    }

    private fun startProgressAnimation() {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 3000
        animator.addUpdateListener { valueAnimator ->
            val progress = valueAnimator.animatedValue as Int
            binding.pbProgressbar.progress = progress
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                fadeOutViews()
                fadeInEstimateReady()
            }
        })
        animator.start()
    }

    private fun startImageChangeAnimation() {
        imageAnimationCoroutine = GlobalScope.launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    binding.ivAnimation.setImageResource(imageResources[currentIndex])
                    currentIndex = (currentIndex + 1) % imageResources.size
                }
                delay(1000)
            }
        }
    }

    private fun startImageAndTextAnimation() {
        imageAndTextAnimationCoroutine = GlobalScope.launch(Dispatchers.Main) {
            while (isActive) {
                // 엔진
                binding.ivLoadEngine.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadEngine.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )
                binding.tvLoadEngine.text = "엔진 장착 중!"
                delay(500)
                binding.ivLoadEngine.setImageResource(R.drawable.ic_loading3)
                binding.tvLoadEngine.text = "엔진 장착 완료!"
                delay(500)

                // 도색
                binding.ivLoadColor.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadColor.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )
                binding.tvLoadColor.text = "도색하는 중!"
                delay(500)
                binding.ivLoadColor.setImageResource(R.drawable.ic_loading3)
                binding.tvLoadColor.text = "도색 완료!"
                delay(500)

                // 옵션
                binding.ivLoadOption.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )
                binding.tvLoadOption.text = "옵션 장착 중!"
                delay(500)
                binding.ivLoadOption.setImageResource(R.drawable.ic_loading3)
                binding.tvLoadOption.text = "옵션 장착 완료!"
                delay(500)
            }
        }
    }

    private fun fadeOutViews() {
        val fadeOutDuration = 500L

        binding.pbProgressbar.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.ivAnimation.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.tvDescription.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.ivLoadEngine.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.tvLoadEngine.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.ivLoadColor.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.tvLoadColor.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.ivLoadOption.animate().alpha(0f).setDuration(fadeOutDuration).start()
        binding.tvLoadOption.animate().alpha(0f).setDuration(fadeOutDuration).start()
    }

    private fun fadeInEstimateReady() {
        val fadeInDuration = 500L
        binding.layoutEstimateReady.root.visibility = View.VISIBLE
        binding.layoutEstimateReady.root.alpha = 0f
        binding.layoutEstimateReady.root.animate().alpha(1f).setDuration(fadeInDuration).start()

        view?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // parent의 너비와 높이는 0이상인경우
                AnimationUtils.explodeView(binding.layoutEstimateReady.flParticleContainer)
                //리스너 제거
                view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageAnimationCoroutine?.cancel()
        imageAndTextAnimationCoroutine?.cancel()
    }
}