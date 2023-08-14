package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentPreliminariesAgeBinding
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel
import com.youngcha.ohmycarset.ui.adapter.recyclerview.AgeAdapter
import com.youngcha.ohmycarset.util.decorator.LinearItemDecoration

class AgeFragment : Fragment() {
    private lateinit var viewModel: UserTagViewModel
    private var _binding: FragmentPreliminariesAgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AgeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreliminariesAgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserTagViewModel::class.java]
        adapter = AgeAdapter(viewModel)
        adapter.setAgeClickEvent { selectedAge ->
            viewModel.updateSelectedAge(selectedAge)
        }

        binding.rvTag.layoutManager = LinearLayoutManager(activity)
        binding.rvTag.adapter = adapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.rvTag.addItemDecoration(LinearItemDecoration(spacingInPixels))

        viewModel.ageList.observe(viewLifecycleOwner) { updatedAgeList ->
            adapter.ageList = updatedAgeList
        }

        binding.btnNext.setOnClickListener {
            //gender 선택 화면으로 전환
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}