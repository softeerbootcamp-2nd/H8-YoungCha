package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentSelfmodePowertrainBinding
import com.youngcha.ohmycarset.ui.interfaces.ComponentClick
import com.youngcha.ohmycarset.viewmodel.CarComponentViewModel

class PowerTrainFragment : Fragment(), ComponentClick {
    //private val args:PowerTrainFragmentArgs by navArgs()

    //VideModel
    private val viewModel: CarComponentViewModel by viewModels()

    //Binding
    private var _binding: FragmentSelfmodePowertrainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelfmodePowertrainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupOptionClickListener(binding.clOption1.clBtn, 1)
        setupOptionClickListener(binding.clOption2.clBtn, 2)
    }

    private fun observeViewModel() {
        viewModel.powerTrainData.observe(viewLifecycleOwner) { powertrain ->
            binding.powertrain = powertrain
            binding.clOption1.powertrain = powertrain
            binding.clOption2.powertrain = powertrain
        }
    }

    private fun setupOptionClickListener(view: View, componentType: Int) {
        view.setOnClickListener {
            onComponentClicked(componentType)
        }
    }

    override fun onComponentClicked(componentType: Int) {
        val hyundaiBlue = ContextCompat.getColor(requireContext(), R.color.main_hyundai_blue)
        val coolBlack = ContextCompat.getColor(requireContext(), R.color.cool_grey_black)
        val coolGrey3 = ContextCompat.getColor(requireContext(), R.color.cool_grey_003)

        when (componentType) {
            1 -> {
                binding.clOption2.ivCheck.setImageResource(R.drawable.ic_check_off)
                binding.clOption2.tvRate.setTextColor(coolGrey3)
                binding.clOption2.tvName.setTextColor(coolGrey3)
                binding.clOption2.tvPrice.setTextColor(coolGrey3)
                binding.clOption2.vBackground.setBackgroundResource(R.drawable.btn_unselect_style)

                binding.clOption1.ivCheck.setImageResource(R.drawable.ic_check_on)
                binding.clOption1.tvRate.setTextColor(hyundaiBlue)
                binding.clOption1.tvName.setTextColor(coolBlack)
                binding.clOption1.tvPrice.setTextColor(coolBlack)
                binding.clOption1.vBackground.setBackgroundResource(R.drawable.btn_select_style)
            }

            2 -> {
                binding.clOption1.ivCheck.setImageResource(R.drawable.ic_check_off)
                binding.clOption1.tvRate.setTextColor(coolGrey3)
                binding.clOption1.tvName.setTextColor(coolGrey3)
                binding.clOption1.tvPrice.setTextColor(coolGrey3)
                binding.clOption1.vBackground.setBackgroundResource(R.drawable.btn_unselect_style)

                binding.clOption2.ivCheck.setImageResource(R.drawable.ic_check_on)
                binding.clOption2.tvRate.setTextColor(hyundaiBlue)
                binding.clOption2.tvName.setTextColor(coolBlack)
                binding.clOption2.tvPrice.setTextColor(coolBlack)
                binding.clOption2.vBackground.setBackgroundResource(R.drawable.btn_select_style)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
