package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.youngcha.ohmycarset.R

class HeaderToolBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_header_toolbar, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HeaderToolBar)
        val title = attributes.getString(R.styleable.HeaderToolBar_title)
        val modeChangeValid = attributes.getInteger(R.styleable.HeaderToolBar_modeChangeValid, 1)
        attributes.recycle()

        val titleTextView: TextView = findViewById(R.id.tv_header_toolbar_title)
        titleTextView.text = title

        val modeChangeButton: ImageButton = findViewById(R.id.ib_mode_change)
        if (modeChangeValid == 0) modeChangeButton.visibility = GONE
    }
}