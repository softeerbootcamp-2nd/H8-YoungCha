package com.youngcha.ohmycarset.ui.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.DialogButtonBinding
import com.youngcha.ohmycarset.model.dialog.ButtonDialog

class ButtonDialogView(context: Context, private val buttonDialogData: ButtonDialog): Dialog(context, R.style.TransparentDialog) {

    private var horizontalButtonClickListener: ((String) -> Unit)? = null
    private var verticalButtonClickListener: ((String) -> Unit)? = null

    fun setOnHorizontalButtonClickListener(listener: (String) -> Unit) {
        horizontalButtonClickListener = listener
    }

    fun setOnVerticalButtonClickListener(listener: (String) -> Unit) {
        verticalButtonClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<DialogButtonBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_button,
            null,
            false
        )

        setContentView(binding.root)

        binding.buttonDialog = buttonDialogData
        binding.layoutDialogVerticalButton.buttonVertical = buttonDialogData.buttonVertical

        binding.layoutDialogHorizontalButton.buttonHorizontal = buttonDialogData.buttonHorizontal

        binding.layoutDialogHorizontalButton.btnLeft.setOnClickListener {
            horizontalButtonClickListener?.invoke(buttonDialogData.buttonHorizontal.btnText1)
            dismiss()
        }

        binding.layoutDialogHorizontalButton.btnRight.setOnClickListener {
            horizontalButtonClickListener?.invoke(buttonDialogData.buttonHorizontal.btnText2)
            dismiss()
        }

        binding.layoutDialogVerticalButton.vSelfMode.setOnClickListener{
            verticalButtonClickListener?.invoke("SelfMode")
            dismiss()
        }

        binding.layoutDialogVerticalButton.vGuideMode.setOnClickListener {
            verticalButtonClickListener?.invoke("GuideMode")
            dismiss()
        }
    }
}