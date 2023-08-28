package com.youngcha.ohmycarset.ui.customview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.data.model.Baekcasajeon
import com.youngcha.ohmycarset.databinding.DialogBaekcasajeonBinding

class BaekcasajeonDialogView(private val context: Context) {
    private var onDismissAction: (() -> Unit)? = null
    private var dialog: Dialog? = null

    fun setOnDismissAction(action: () -> Unit) {
        this.onDismissAction = action
    }

    fun show(baekcasajeon: Baekcasajeon) {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<DialogBaekcasajeonBinding>(
            inflater, R.layout.dialog_baekcasajeon, null, false
        )
        binding.title = baekcasajeon.word
        binding.description = baekcasajeon.description
        binding.imgUrl = baekcasajeon.imgUrl

        // 여기서 vUnderstand 클릭 리스너를 설정합니다.
        binding.vUnderstand.setOnClickListener {
            dismiss()
        }

        dialog = Dialog(context).apply {
            setContentView(binding.root)
            setCancelable(true)  // 백 버튼으로 다이얼로그를 닫을 수 있도록 설정
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            window?.setLayout(
                (375 * context.resources.displayMetrics.density).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT  // 세로 높이는 원래대로 유지
            )

            setOnDismissListener {
                onDismissAction?.invoke()
            }
        }

        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}