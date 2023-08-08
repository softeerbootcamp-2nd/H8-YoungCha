package com.youngcha.ohmycarset.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.youngcha.ohmycarset.databinding.FragmentMakeCarSelfModeBinding

class MakeCarSelfModeFragment : Fragment() {
    private var _binding: FragmentMakeCarSelfModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeCarSelfModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TEST
        val tabNames = listOf(
            "01 파워트레인",
            "02 구동 방식",
            "03 바디 타입",
            "04 외장 색상",
            "05 내장 색상",
            "06 휠 선택",
            "07 옵션 선택",
            "08 견적 내기"
        )

        // 각 탭 추가
        tabNames.forEach { name ->
            binding.makeCarTabLayout.addTab(binding.makeCarTabLayout.newTab().setText(name))
        }

        binding.makeCarTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabName = tab?.text ?: "Unknown" // 탭의 이름 가져오기. 없으면 "Unknown"으로 처리.

                // Snackbar 사용하여 탭의 이름 표시하기.
                Snackbar.make(binding.root, "Selected tab: $tabName", Snackbar.LENGTH_SHORT).show()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }


}