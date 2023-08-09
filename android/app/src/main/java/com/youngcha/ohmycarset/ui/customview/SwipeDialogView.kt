package com.youngcha.ohmycarset.ui.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.DialogSwipeBinding
import com.youngcha.ohmycarset.model.dialog.SwipeDialog
import com.youngcha.ohmycarset.ui.adapter.dialog.SwipeAdapter

class SwipeDialogView(
    context: Context,
    private val swipeDialogData: List<SwipeDialog>,
) : Dialog(context, R.style.TransparentDialog) {

    private val dataSize = swipeDialogData.size
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<DialogSwipeBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_swipe,
            null,
            false
        )
        setContentView(binding.root)

        binding.handler = this

        viewPager = binding.vpContainer

        val adapter = SwipeAdapter(swipeDialogData)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = binding.tbIndicator
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }

    fun onPrevClicked() {
        if (viewPager.currentItem > 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        }
    }

    fun onNextClicked() {
        if (viewPager.currentItem < dataSize - 1) {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }
    }

    fun onExitClicked() {
        dismiss()
    }

}