package com.youngcha.ohmycarset.util

import android.graphics.Color
import android.graphics.Rect
import android.text.Spannable
import android.text.style.ClickableSpan
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.youngcha.baekcasajeon.AnimatedRoundedBackgroundSpan
import com.youngcha.baekcasajeon.KeywordOptions
import com.youngcha.baekcasajeon.baekcasajeon
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.data.model.Baekcasajeon
import com.youngcha.ohmycarset.ui.customview.BaekcasajeonDialogView

// TextViewExtensions.kt

fun TextView.showBaekcasajeon(baekcasajeons: List<Baekcasajeon>) {
    val dialog = BaekcasajeonDialogView(this.context)

    val options = baekcasajeons.associate { baekcasajeon ->
        baekcasajeon.word to KeywordOptions(
            nonSelectedTextColor = Color.BLACK,  // 선택되지 않았을 때의 텍스트 색상
            selectedTextColor = Color.WHITE,     // 선택되었을 때의 텍스트 색상
            nonSelectedBackgroundColor = ContextCompat.getColor(
                this.context,
                R.color.yellow
            ),
            selectedBackgroundColor = ContextCompat.getColor(
                this.context,
                R.color.cool_grey_black
            ),
            padding = Rect(15, 15, 15, 15),
            cornerRadius = 20f,
            isBold = true
        )
    }

    val keywordSpans = this.baekcasajeon(options) { keyword ->
        // baekcasajeonList에서 해당 keyword와 일치하는 Baekcasajeon 객체를 찾는다.
        val matchingBaekcasajeon = baekcasajeons.find { it.word == keyword }

        matchingBaekcasajeon.let {
            if (it != null) {
                dialog.show(it)
            }
        }

    }

    dialog.setOnDismissAction {
        for (baekcasajeon in baekcasajeons) {
            keywordSpans[baekcasajeon.word]?.toggleSelected()
        }
    }
}

fun TextView.hideBaekcasajeon() {
    val textContent = this.text
    if (textContent is Spannable) {
        // AnimatedRoundedBackgroundSpan을 제거
        val backgroundSpans = textContent.getSpans(0, textContent.length, AnimatedRoundedBackgroundSpan::class.java)
        for (span in backgroundSpans) {
            textContent.removeSpan(span)
        }

        // ClickableSpan도 제거
        val clickableSpans = textContent.getSpans(0, textContent.length, ClickableSpan::class.java)
        for (span in clickableSpans) {
            textContent.removeSpan(span)
        }
    }

    this.movementMethod = null  // 클릭 이벤트를 제거
    this.text = textContent  // 스팬을 제거한 후 텍스트 뷰를 다시 갱신
}

