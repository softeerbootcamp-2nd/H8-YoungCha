package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.FragmentGuideModeBinding
import com.youngcha.ohmycarset.databinding.FragmentSelfModeBinding

class SelfModeFragment : Fragment() {
    private val args: SelfModeFragmentArgs by navArgs()
    private var _binding: FragmentSelfModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelfModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedTrimName = args.SelectedTrimName

        Log.d("로그", selectedTrimName.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}