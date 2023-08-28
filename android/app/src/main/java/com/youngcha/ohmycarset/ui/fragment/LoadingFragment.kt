package com.youngcha.ohmycarset.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.data.api.RetrofitClient
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.data.repository.CategoryRepository
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import com.youngcha.ohmycarset.data.repository.SelfModeRepository
import com.youngcha.ohmycarset.databinding.FragmentLoadingBinding
import com.youngcha.ohmycarset.util.AnimationUtils.explodeView
import com.youngcha.ohmycarset.util.getColorCodeFromName
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel
import com.youngcha.ohmycarset.viewmodel.GuideModeViewModel
import com.youngcha.ohmycarset.viewmodel.factory.CarCustomizationViewModelFactory
import com.youngcha.ohmycarset.viewmodel.factory.GuideModeViewModelFactory
import kotlinx.coroutines.*


class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")

    private val imageResources = listOf(R.drawable.ic_change, R.drawable.ic_model_change)
    private var currentIndex = 0
    private var imageAnimationCoroutine: Job? = null
    private var imageAndTextAnimationCoroutine: Job? = null

    private val selfModeRepository by lazy { SelfModeRepository(RetrofitClient.selfModeApi) }
    private val guideModeRepository by lazy { GuideModeRepository(RetrofitClient.guideModeApi) }
    private val categoryRepository by lazy { CategoryRepository(RetrofitClient.categoriesApi) }
    private lateinit var guideParam: GuideParam

    private lateinit var carCustomizationViewModel: CarCustomizationViewModel

    // guideModeViewModel은 접근이 가능하도록 lateinit으로 선언
    private lateinit var guideModeViewModel: GuideModeViewModel

    private var isCarSelected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = CarCustomizationViewModelFactory(selfModeRepository, guideModeRepository, categoryRepository)
        carCustomizationViewModel = ViewModelProvider(requireActivity(), factory).get(CarCustomizationViewModel::class.java)

        // LoadingFragmentArgs 객체를 얻습니다.
        val args = LoadingFragmentArgs.fromBundle(requireArguments())
        // GuideParam 객체를 얻습니다.
        guideParam = args.guideParam!!

        carCustomizationViewModel.startGuideMode(guideParam)

        carCustomizationViewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            binding.layoutEstimateReady.imageUrl = "https://www.hyundai.com/contents/vr360/LX06/exterior/WC9/001.png"
            isCarSelected = car != null
            if (isCarSelected) {
                startProgressAnimation(false)
            }
        }

        continuousAnimations()
        setupClickListener()
    }

    private fun continuousAnimations() {
        startProgressAnimation(true)
        startImageChangeAnimation()
        startImageAndTextAnimation()
    }

    private fun stopAllAnimations() {
        imageAnimationCoroutine?.cancel()
        imageAndTextAnimationCoroutine?.cancel()
        // 다른 애니메이션 중단 코드도 여기에 추가하세요
    }


    private fun initGuideModeViewModel() {
        val factory = GuideModeViewModelFactory(guideModeRepository, categoryRepository, guideParam)
        guideModeViewModel = ViewModelProvider(this, factory).get(GuideModeViewModel::class.java)
    }

    private fun setupClickListener() {
        var bundle: Bundle?
        binding.layoutEstimateReady.btnNext.setOnClickListener {
            bundle = Bundle().apply {
                putString("mode", "GuideMode")
                putString("startPoint", "start")
            }
            findNavController().navigate(
                R.id.action_loadingFragment_to_makeCarFragment,
                bundle
            )
        }

        binding.layoutEstimateReady.btnSkip.setOnClickListener {
            bundle = Bundle().apply {
                putString("mode", "GuideMode")
                putString("startPoint", "end")
            }
            findNavController().navigate(
                R.id.action_loadingFragment_to_makeCarFragment,
                bundle
            )
        }
    }

    private fun startProgressAnimation(isInfinite: Boolean) {
        if (_binding == null || !isAdded) return
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 3000
        animator.addUpdateListener { valueAnimator ->
            val progress = valueAnimator.animatedValue as Int
            if (!isAdded) return@addUpdateListener
            binding?.pbProgressbar?.progress = progress
        }
        if (isInfinite) {
            animator.repeatCount = ValueAnimator.INFINITE
        } else {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fadeOutViews()
                    fadeInEstimateReady()
                }
            })
        }
        animator.start()
    }

    private fun startImageChangeAnimation() {
        imageAnimationCoroutine = lifecycleScope.launch {
            while (isActive) {
                if (!isAdded) return@launch
                withContext(Dispatchers.Main) {
                    binding.ivAnimation.setImageResource(imageResources[currentIndex])
                    currentIndex = (currentIndex + 1) % imageResources.size
                }
                delay(1000)
            }
        }
    }

    private fun startImageAndTextAnimation() {
        imageAndTextAnimationCoroutine = lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                if (!isAdded) return@launch
                // 엔진
                binding.ivLoadEngine.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadEngine.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )

                if (!isCarSelected) {
                    binding.tvLoadEngine.text = "엔진 장착 중.."
                    delay(500)
                    binding.ivLoadEngine.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadEngine.text = "엔진 장착 중..."
                    delay(500)
                } else {
                    binding.tvLoadEngine.text = "엔진 장착 중!"
                    delay(500)
                    binding.ivLoadEngine.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadEngine.text = "엔진 장착 완료!"
                    delay(500)
                }

                // 도색
                binding.ivLoadColor.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadColor.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )

                if (!isCarSelected) {
                    binding.tvLoadColor.text = "도색하는 중.."
                    delay(500)
                    binding.ivLoadColor.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadColor.text = "도색하는 중..."
                    delay(500)
                } else {
                    binding.tvLoadColor.text = "도색하는 중!"
                    delay(500)
                    binding.ivLoadColor.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadColor.text = "도색 완료!"
                    delay(500)
                }

                // 옵션
                binding.ivLoadOption.setImageResource(R.drawable.ic_loading1)
                binding.tvLoadOption.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_hyundai_blue
                    )
                )

                if (!isCarSelected) {
                    binding.tvLoadOption.text = "옵션 장착 중.."
                    delay(500)
                    binding.ivLoadOption.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadOption.text = "옵션 장착 중..."
                    delay(500)
                } else {
                    binding.tvLoadOption.text = "옵션 장착 중!"
                    delay(500)
                    binding.ivLoadOption.setImageResource(R.drawable.ic_loading3)
                    binding.tvLoadOption.text = "옵션 장착 완료!"
                    delay(500)
                }
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
                explodeView(binding.layoutEstimateReady.flParticleContainer)
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