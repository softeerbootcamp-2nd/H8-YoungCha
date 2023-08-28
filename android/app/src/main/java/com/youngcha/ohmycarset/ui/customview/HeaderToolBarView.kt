package com.youngcha.ohmycarset.ui.customview

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.ViewHeaderToolbarBinding
import com.youngcha.ohmycarset.ui.interfaces.OnHeaderToolbarClickListener

class HeaderToolBarView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ViewHeaderToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    private val titleTextView: TextView = binding.tvHeaderToolbarTitle

    var listener: OnHeaderToolbarClickListener? = null

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HeaderToolBar)
        val title = attributes.getString(R.styleable.HeaderToolBar_title)
        val modeChangeValid = attributes.getInteger(R.styleable.HeaderToolBar_modeChangeValid, 1)
        attributes.recycle()

        titleTextView.text = title

        if (modeChangeValid == 0) binding.ibModeChange.visibility = GONE

        // Set click listeners using binding
        binding.ibExit.setOnClickListener { listener?.onExitClick() }
        binding.ibModeChange.setOnClickListener { listener?.onModeChangeClick() }
        binding.ibDictionaryOff.setOnClickListener { listener?.onDictionaryOffClick() }
        binding.ibModelChange.setOnClickListener { listener?.onModelChangeClick() }
    }

    fun setTitle(currentType: String) {
        when(currentType) {
            "GuideMode" -> {
                titleTextView.text = "가이드 모드"
                titleTextView.setTextColor(ContextCompat.getColor(context, R.color.active_blue))
            }
            "SelfMode" -> {
                titleTextView.text = "셀프 모드"
                titleTextView.setTextColor(ContextCompat.getColor(context, R.color.main_hyundai_blue))
            }
        }
    }

    fun updateDictionaryState(state: Int, animate: Boolean = false) {
        val dictionaryIcon = binding.ibDictionaryOff
        dictionaryIcon.alpha = 1f  // 항상 초기 alpha값을 1로 설정

        if (state == 1) {
            if (animate) {
                animateIcon(dictionaryIcon, R.drawable.ic_dictionary_on, R.drawable.ic_dictionary_off)
            } else {
                dictionaryIcon.setImageResource(R.drawable.ic_dictionary_on)
            }
        } else {
            if (animate) {
                animateIcon(dictionaryIcon, R.drawable.ic_dictionary_off, R.drawable.ic_dictionary_on)
            } else {
                dictionaryIcon.setImageResource(R.drawable.ic_dictionary_off)
            }
        }
    }


    private fun animateIcon(icon: ImageView, newResource: Int, oldResource: Int) {
        val fadeOut = ObjectAnimator.ofFloat(icon, "alpha", 1f, 0f)
        fadeOut.duration = 300
        fadeOut.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                icon.setImageResource(newResource)
                fadeIn(icon).start()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}
        })
        fadeOut.start()
    }

    private fun fadeIn(view: ImageView): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
            duration = 300
        }
    }
}
