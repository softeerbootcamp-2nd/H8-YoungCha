package com.youngcha.ohmycarset.ui.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.DialogBaekcasajeonBinding

class BaekcasajeonDialogView(private val anchorView: View) {
    private lateinit var popupWindow: PopupWindow
    private var onDismissAction: (() -> Unit)? = null // 이 리스너를 통해 PopupWindow가 닫힐 때의 동작을 설정합니다.

    fun setOnDismissAction(action: () -> Unit) {
        this.onDismissAction = action
    }


    fun show(title: String, description: String) {
        val inflater = LayoutInflater.from(anchorView.context)
        val binding = DataBindingUtil.inflate<DialogBaekcasajeonBinding>(
            inflater, R.layout.dialog_baekcasajeon, null, false
        )
        binding.title = title
        binding.description = description

        // 여기서 vUnderstand 클릭 리스너를 설정합니다.
        binding.vUnderstand.setOnClickListener {
            dismiss()
        }

        popupWindow = PopupWindow(
            binding.root,
            (380 * anchorView.resources.displayMetrics.density).toInt(), // dp to px
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            setOnDismissListener {
                onDismissAction?.invoke()
            }

        }

        // 원하는 margin 값을 설정합니다.
        val yOffset = (12 * anchorView.resources.displayMetrics.density).toInt()  // 12dp를 픽셀로 변환

        popupWindow.showAsDropDown(anchorView, 0, yOffset)  // xOffset는 기본값인 0을 사용합니다.

    }

    fun dismiss() {
        if (::popupWindow.isInitialized && popupWindow.isShowing) {
            popupWindow.dismiss()
        }
    }
}