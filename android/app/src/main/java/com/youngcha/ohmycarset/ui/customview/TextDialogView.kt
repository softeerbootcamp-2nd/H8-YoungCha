package com.youngcha.ohmycarset.ui.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.DialogTextBinding
import com.youngcha.ohmycarset.model.dialog.TextDialog

class TextDialogView(context: Context, private val textDialogData: TextDialog): Dialog(context, R.style.TransparentDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<DialogTextBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_text,
            null,
            false
        )

        setContentView(binding.root)

        binding.handler = this
        binding.textDialog = textDialogData
    }

    fun onExitClicked() {
        dismiss()
    }
}