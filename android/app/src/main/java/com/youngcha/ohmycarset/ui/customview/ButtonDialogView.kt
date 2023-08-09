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
    }
}