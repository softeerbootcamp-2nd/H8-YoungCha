package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.ui.interfaces.OnHeaderToolbarClickListener

class HeaderToolBarView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val titleTextView: TextView

    var listener: OnHeaderToolbarClickListener? = null

    init {
        inflate(context, R.layout.view_header_toolbar, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HeaderToolBar)
        val title = attributes.getString(R.styleable.HeaderToolBar_title)
        val modeChangeValid = attributes.getInteger(R.styleable.HeaderToolBar_modeChangeValid, 1)
        attributes.recycle()

        titleTextView = findViewById(R.id.tv_header_toolbar_title)
        titleTextView.text = title

        val modeChangeButton: ImageButton = findViewById(R.id.ib_mode_change)
        if (modeChangeValid == 0) modeChangeButton.visibility = GONE

        // Set click listeners
        findViewById<ImageButton>(R.id.ib_exit).setOnClickListener { listener?.onExitClick() }
        modeChangeButton.setOnClickListener { listener?.onModeChangeClick() }
        findViewById<ImageButton>(R.id.ib_dictionary_off).setOnClickListener { listener?.onDictionaryOffClick() }
        findViewById<ImageButton>(R.id.ib_model_change).setOnClickListener { listener?.onModelChangeClick() }
    }

    fun setTitle(currentType: String) {
        when(currentType) {
            "GuideMode" -> titleTextView.text = "가이드 모드"
            "SelfMode" -> titleTextView.text = "셀프 모드"
        }
    }
}