package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentPreliminariesKeywordBinding
import com.youngcha.ohmycarset.ui.adapter.recyclerview.ImportantAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.StrengthAdapter
import com.youngcha.ohmycarset.ui.adapter.recyclerview.UseAdapter
import com.youngcha.ohmycarset.util.decorator.GridItemDecoration
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class KeywordFragment : Fragment() {
    private lateinit var viewModel: UserTagViewModel
    private var _binding: FragmentPreliminariesKeywordBinding? = null
    private val binding get() = _binding!!

    private lateinit var strengthAdapter: StrengthAdapter
    private lateinit var importantAdapter: ImportantAdapter
    private lateinit var useAdapter: UseAdapter

    private var selectedItemsCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreliminariesKeywordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UserTagViewModel::class.java]

        strengthAdapter = StrengthAdapter(viewModel)
        importantAdapter = ImportantAdapter(viewModel)
        useAdapter = UseAdapter(viewModel)

        strengthAdapter.setStrengthClickEvent { selectedStrength ->
            viewModel.updateSelectedStrength(selectedStrength)
            selectedItemsCount += if (selectedStrength.isSelected) 1 else -1
            updateNextButtonColor()
        }

        importantAdapter.setImportantClickEvent { selectedImportant ->
            viewModel.updateSelectedImportant(selectedImportant)
            selectedItemsCount += if (selectedImportant.isSelected) 1 else -1
            updateNextButtonColor()
        }

        useAdapter.setUseClickEvent { selectedUse ->
            viewModel.updateSelectedUse(selectedUse)
            selectedItemsCount += if (selectedUse.isSelected) 1 else -1
            updateNextButtonColor()
        }


        binding.rvStrength.layoutManager = GridLayoutManager(activity, 2)
        binding.rvStrength.adapter = strengthAdapter

        binding.rvImportant.layoutManager = GridLayoutManager(activity, 2)
        binding.rvImportant.adapter = importantAdapter

        binding.rvUses.layoutManager = GridLayoutManager(activity, 2)
        binding.rvUses.adapter = useAdapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.rvStrength.addItemDecoration(GridItemDecoration(2, spacingInPixels, false))
        binding.rvImportant.addItemDecoration(GridItemDecoration(2, spacingInPixels, false))
        binding.rvUses.addItemDecoration(GridItemDecoration(2, spacingInPixels, false))

        viewModel.strengthList.observe(viewLifecycleOwner) { updatedStrengthList ->
            strengthAdapter.strengthList = updatedStrengthList
        }
        viewModel.importantList.observe(viewLifecycleOwner) { updateImportantList ->
            importantAdapter.importantList = updateImportantList
        }
        viewModel.useList.observe(viewLifecycleOwner) { updateUseList ->
            useAdapter.useList = updateUseList
        }

        binding.btnNext.setOnClickListener {
            if (selectedItemsCount == 3) {
                //로딩 프래그먼트로 전환
            }
        }
        binding.tvSkip.setOnClickListener {
            viewModel.resetSelection()
            //로딩 프래그먼트로 전환
        }
    }

    private fun updateNextButtonColor() {
        if (selectedItemsCount == 3) {
            binding.btnNext.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.main_hyundai_blue
            )
        } else {
            binding.btnNext.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cool_grey_003
                )
            )
            binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                R.color.cool_grey_001
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}