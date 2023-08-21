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
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.enums.TrimType
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

        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 3000
        animator.addUpdateListener { valueAnimator ->
            val progress = valueAnimator.animatedValue as Int
            binding.pbProgressbar.progress = progress
        }
        animator.start()
        imageAnimationCoroutine = GlobalScope.launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    binding.ivAnimation.setImageResource(imageResources[currentIndex])
                    currentIndex = (currentIndex + 1) % imageResources.size
                }
                delay(1000) // 1초마다 이미지 변경
            }
        }

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


        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                //화면 전환 : 견적보기 애니메이션으로 전환
                var bundle: Bundle?

                bundle = Bundle().apply {
                    putString("mode", "GuideMode")
                }
                findNavController().navigate(
                    R.id.action_loadingFragment_to_makeCarSelfModeFragment,
                    bundle
                )
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