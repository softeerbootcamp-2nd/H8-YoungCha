package com.youngcha.ohmycarset.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentPreliminariesGenderBinding
import com.youngcha.ohmycarset.ui.adapter.recyclerview.GenderAdapter
import com.youngcha.ohmycarset.util.decorator.LinearItemDecoration
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class GenderFragment : Fragment() {
    private lateinit var viewModel: UserTagViewModel
    private var _binding: FragmentPreliminariesGenderBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreliminariesGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserTagViewModel::class.java]
        adapter = GenderAdapter(viewModel)
        adapter.setGenderClickEvent { selectedGender ->
            viewModel.updateSelectedGender(selectedGender)
        }

        binding.rvTag.layoutManager = LinearLayoutManager(activity)
        binding.rvTag.adapter = adapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.rvTag.addItemDecoration(LinearItemDecoration(spacingInPixels))

        viewModel.genderList.observe(viewLifecycleOwner) { updatedGenderList ->
            adapter.genderList = updatedGenderList
        }

        binding.btnNext.setOnClickListener {
            //keyword  화면으로 전환
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}