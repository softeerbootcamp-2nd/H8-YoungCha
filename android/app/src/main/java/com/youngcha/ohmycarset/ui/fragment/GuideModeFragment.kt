package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.youngcha.ohmycarset.databinding.FragmentGuideModeBinding

class GuideModeFragment : Fragment() {
    private val args: GuideModeFragmentArgs by navArgs()
    private var _binding: FragmentGuideModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuideModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedTrimName = args.selectedTrimName

        Log.d("로그", selectedTrimName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}