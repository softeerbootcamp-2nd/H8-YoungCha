package com.youngcha.ohmycarset.util

import android.content.Context
import com.youngcha.ohmycarset.data.model.car.OptionInfo
import com.youngcha.ohmycarset.data.model.dialog.SwipeDialog
import com.youngcha.ohmycarset.data.model.dialog.TextDialog
import com.youngcha.ohmycarset.ui.customview.SwipeDialogView
import com.youngcha.ohmycarset.ui.customview.TextDialogView

fun showSwipeDialog(
    context: Context,
    optionInfo: OptionInfo
) {
    val dialogs = mutableListOf<SwipeDialog>()
    optionInfo.detail.forEach {
        dialogs.add(SwipeDialog(it.imgUrl, optionInfo.name, it.name, it.description))
    }
    SwipeDialogView(context, dialogs).show()
}

fun showTextDialog(
    context: Context,
    componentName: String,
    optionInfo: OptionInfo
) {
    var spec: String = ""
    optionInfo.detail[0].specs.forEach {
        spec += "${it.name}  ${it.description}\n"
    }
    TextDialogView(context, TextDialog(componentName, optionInfo.name, optionInfo.detail[0].description, spec)).show()
}
