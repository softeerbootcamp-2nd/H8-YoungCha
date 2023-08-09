package com.youngcha.ohmycarset.ui.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.DialogButtonBinding
import com.youngcha.ohmycarset.model.dialog.ButtonDialog
import com.youngcha.ohmycarset.ui.interfaces.OnButtonDialogCallback

class ButtonDialogView(context: Context, private val buttonDialogData: ButtonDialog): Dialog(context, R.style.TransparentDialog) {

    private var callback: OnButtonDialogCallback? = null

    fun setButtonDialogCallback(callback: OnButtonDialogCallback) {
        this.callback = callback
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

        binding.btnLeft.setOnClickListener {
            callback?.onButtonClicked(buttonDialogData.btnText1)
            dismiss()
        }

        binding.btnRight.setOnClickListener {
            callback?.onButtonClicked(buttonDialogData.btnText2)
            dismiss()
        }
    }
}