package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentGuidemodePreliminariesBinding
import com.youngcha.ohmycarset.enums.PreliminariesStepType
import com.youngcha.ohmycarset.ui.adapter.recyclerview.AgeAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.GenderAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.ImportantAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.StrengthAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.UseAdapter
import com.youngcha.ohmycarset.util.decorator.GridItemDecoration
import com.youngcha.ohmycarset.util.decorator.LinearItemDecoration
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EstimateReadyFragment : Fragment() {
    private var _binding: FragmentGuidemodePreliminariesBinding? = null
    private val binding get() = _binding!!

    private val userTagViewModel: UserTagViewModel by viewModels()

    private lateinit var ageAdapter: AgeAdapter
    private lateinit var genderAdapter: GenderAdapter
    private lateinit var strengthAdapter: StrengthAdapter
    private lateinit var importantAdapter: ImportantAdapter
    private lateinit var useAdapter: UseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuidemodePreliminariesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutPreliminariesKeyword.viewModel=userTagViewModel
        binding.layoutPreliminariesKeyword.lifecycleOwner=this

        initView()
        setRecyclerView()
        observeViewModel()
        particleAnimation()
        onClickFunction()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView() {
        binding.layoutPreliminariesAge.clRootView.visibility = View.VISIBLE
        binding.layoutPreliminariesGender.clRootView.visibility = View.GONE
        binding.layoutPreliminariesKeyword.clRootView.visibility = View.GONE
        binding.layoutEstimateReady.clRootView.visibility = View.GONE
    }


    private fun particleAnimation() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)

            val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

            val animationSet = AnimationSet(true)
            animationSet.addAnimation(slideInAnimation)
            animationSet.addAnimation(fadeInAnimation)

            binding.layoutEstimateReady.ivParticle.visibility = View.VISIBLE
            binding.layoutEstimateReady.ivParticle.startAnimation(animationSet)
        }
    }

    private fun handlePreliminariesStep(type: PreliminariesStepType) {
        when (type) {
            PreliminariesStepType.AGE -> {
                binding.layoutPreliminariesAge.clRootView.visibility = View.GONE
                binding.layoutPreliminariesGender.clRootView.visibility = View.VISIBLE
            }

            PreliminariesStepType.GENDER -> {
                binding.layoutPreliminariesGender.clRootView.visibility = View.GONE
                binding.layoutPreliminariesKeyword.clRootView.visibility = View.VISIBLE
            }

            PreliminariesStepType.KEYWORD -> {
                binding.layoutPreliminariesKeyword.clRootView.visibility = View.GONE
                //원래는 로딩 프래그먼트로 전환
                binding.layoutEstimateReady.clRootView.visibility = View.VISIBLE
            }

            PreliminariesStepType.READY -> {
            }
        }
    }

    private fun observeViewModel() {
        userTagViewModel.ageList.observe(viewLifecycleOwner) { updatedAgeList ->
            ageAdapter.ageList = updatedAgeList
        }

        userTagViewModel.genderList.observe(viewLifecycleOwner) { updatedGenderList ->
            genderAdapter.genderList = updatedGenderList
        }

        userTagViewModel.strengthList.observe(viewLifecycleOwner) { updateStrengthList ->
            strengthAdapter.strengthList = updateStrengthList
        }

        userTagViewModel.importantList.observe(viewLifecycleOwner) { updateImportantList ->
            importantAdapter.importantList = updateImportantList
        }

        userTagViewModel.useList.observe(viewLifecycleOwner) { updateUseList ->
            useAdapter.useList = updateUseList
        }
    }

    private fun setRecyclerView() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)

        // AGE ==========================================================================
        ageAdapter = AgeAdapter(userTagViewModel)
        binding.layoutPreliminariesAge.rvTag.layoutManager = LinearLayoutManager(activity)
        binding.layoutPreliminariesAge.rvTag.adapter = ageAdapter
        binding.layoutPreliminariesAge.rvTag.addItemDecoration(LinearItemDecoration(spacingInPixels))

        binding.layoutPreliminariesAge.btnNext.setOnClickListener {
            handlePreliminariesStep(PreliminariesStepType.AGE)
        }


        //GENDER
        genderAdapter = GenderAdapter(userTagViewModel)
        binding.layoutPreliminariesGender.rvTag.layoutManager = LinearLayoutManager(activity)
        binding.layoutPreliminariesGender.rvTag.adapter = genderAdapter
        binding.layoutPreliminariesGender.rvTag.addItemDecoration(
            LinearItemDecoration(
                spacingInPixels
            )
        )
        binding.layoutPreliminariesGender.btnNext.setOnClickListener {
            handlePreliminariesStep(PreliminariesStepType.GENDER)
        }


        //STRENGTH
        strengthAdapter = StrengthAdapter(userTagViewModel)
        binding.layoutPreliminariesKeyword.rvStrength.layoutManager = GridLayoutManager(activity, 2)
        binding.layoutPreliminariesKeyword.rvStrength.adapter = strengthAdapter
        binding.layoutPreliminariesKeyword.rvStrength.addItemDecoration(
            GridItemDecoration(
                2,
                spacingInPixels,
                false
            )
        )

        //IMPORTANT
        importantAdapter = ImportantAdapter(userTagViewModel)
        binding.layoutPreliminariesKeyword.rvImportant.layoutManager =
            GridLayoutManager(activity, 2)
        binding.layoutPreliminariesKeyword.rvImportant.adapter = importantAdapter
        binding.layoutPreliminariesKeyword.rvImportant.addItemDecoration(
            GridItemDecoration(
                2,
                spacingInPixels,
                false
            )
        )

        //USES
        useAdapter = UseAdapter(userTagViewModel)
        binding.layoutPreliminariesKeyword.rvUses.layoutManager = GridLayoutManager(activity, 2)
        binding.layoutPreliminariesKeyword.rvUses.adapter = useAdapter
        binding.layoutPreliminariesKeyword.rvUses.addItemDecoration(
            GridItemDecoration(
                2,
                spacingInPixels,
                false
            )
        )
    }

    private fun onClickFunction() {
        binding.layoutPreliminariesKeyword.btnNext.setOnClickListener {
            Log.d("Fragment", userTagViewModel.isChange.value.toString())
            if (userTagViewModel.isChange.value == 1) {
                //로딩 프래그먼트로 전환
                handlePreliminariesStep(PreliminariesStepType.KEYWORD)
            }
        }
        binding.layoutPreliminariesKeyword.tvSkip.setOnClickListener {
            userTagViewModel.resetSelection()
            //로딩 프래그먼트로 전환
            handlePreliminariesStep(PreliminariesStepType.KEYWORD)
        }

        binding.layoutEstimateReady.btnNext.setOnClickListener {

        }

        binding.layoutEstimateReady.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.action_estimateReadyFragment_to_loadingFragment)
        }
    }
}