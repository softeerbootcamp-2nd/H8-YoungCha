package com.youngcha.ohmycarset.ui.adapter.binding

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.model.car.OptionInfo
import com.youngcha.ohmycarset.ui.adapter.recyclerview.EstimateSummaryAdapter
import com.youngcha.ohmycarset.ui.customview.CircleView
import com.youngcha.ohmycarset.ui.customview.FeedbackView
import com.youngcha.ohmycarset.ui.customview.HeaderToolBarView
import com.youngcha.ohmycarset.ui.customview.HyundaiButtonView
import com.youngcha.ohmycarset.ui.interfaces.OnHyundaiButtonClickListener
import com.youngcha.ohmycarset.util.RoundedBackgroundSpan

@BindingAdapter(value = ["mainImageUrl", "subImageUrl"], requireAll = false)
fun loadImage(view: ImageView, mainImageUrl: String?, subImageUrl: String?) {
    if (subImageUrl.isNullOrEmpty()) {
        if (mainImageUrl.isNullOrEmpty()) {
            Glide.with(view.context).clear(view)
        } else {
            Glide.with(view.context)
                .load(mainImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    } else {
        Glide.with(view.context)
            .load(subImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter(value = ["detailMainImageUrl", "detailSubImageUrl"], requireAll = false)
fun setLogoImage(view: ImageView, mainImageUrl: String?, subImageUrl: String?) {

    val context = view.context
    val widthInPixels: Int
    val heightInPixels: Int

    if (mainImageUrl == "") {
        widthInPixels = (60 * context.resources.displayMetrics.density).toInt()
        heightInPixels = (60 * context.resources.displayMetrics.density).toInt()
        Glide.with(view.context)
            .load(subImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(view)
    } else {
        widthInPixels = (86 * context.resources.displayMetrics.density).toInt()
        heightInPixels = (64 * context.resources.displayMetrics.density).toInt()
        Glide.with(view.context)
            .load(mainImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    val params = view.layoutParams
    params.width = widthInPixels
    params.height = heightInPixels
    view.layoutParams = params
}

@BindingAdapter("imageUrl")
fun loadImageCoil(view: ImageView, imageUrl: String?) {
    imageUrl.let {
        view.load(it) {
        }
    }
}

@BindingAdapter("svgImageUrl")
fun loadSvgImage(imageView: ImageView, url: String?) {
    val context = imageView.context

    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            add(SvgDecoder(context))
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(url)
        .target(imageView)
        .build()

    imageLoader.enqueue(request)
}

@BindingAdapter("exterior_imageUrl")
fun loadExteriorImage(view: ImageView, imageUrl: String?) {
    imageUrl.let {
        view.load(it) {
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("round_corner_imageUrl")
fun loadInteriorImage(view: ImageView, imageUrl: String?) {
    imageUrl.let {
        view.load(it) {
            transformations(RoundedCornersTransformation(10f))
        }
    }
}


fun setLogoImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(logoImageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("testImageSource")
fun loadImage(view: ImageView, imageUrl: Int) {
    if (imageUrl == 1) return
    view.setImageResource(imageUrl)
}

@BindingAdapter("optionImage")
fun setOptionImage(view: ImageView, imageUrl: Int) {
    if (imageUrl <= 0) return
    view.setImageResource(imageUrl)
}

@BindingAdapter("optionImage_circle")
fun setFillColor(view: CircleView, @ColorRes colorResId: Int?) {
    if (colorResId != null && colorResId != 0) {
        val color = ContextCompat.getColor(view.context, colorResId)
        view.setFillColor(color)
    }
}

@BindingAdapter("app:fillColor")
fun setFillColor(view: CircleView, colorStr: String?) {
    if (!colorStr.isNullOrEmpty()) {
        try {
            view.setFillColor(Color.parseColor(colorStr))
        } catch (e: IllegalArgumentException) {
            // 색상 문자열이 잘못된 경우 처리
        }
    }
}


@BindingAdapter("app:isVisibleForRankZero")
fun View.isVisibleForRankZero(rank: Int) {
    visibility = if (rank == 0) View.VISIBLE else View.GONE
}

@BindingAdapter("dynamicWidth")
fun setDynamicWidth(cardView: CardView, widthDp: Float) {
    val widthPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        widthDp,
        cardView.resources.displayMetrics
    )
    val layoutParams = cardView.layoutParams
    layoutParams.width = widthPx.toInt()
    cardView.layoutParams = layoutParams
}

@BindingAdapter("dynamicHeight")
fun setDynamicHeight(cardView: CardView, heightDp: Float) {
    val heightPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        heightDp,
        cardView.resources.displayMetrics
    )
    val layoutParams = cardView.layoutParams
    layoutParams.height = heightPx.toInt()
    cardView.layoutParams = layoutParams
}

@BindingAdapter("car", "componentName")
fun setOptionsVisibility(view: View, car: Car?, componentName: String?) {
    car?.mainOptions?.find { it.keys.first() == componentName }?.values?.first()?.let {
        view.visibility = if (it.size >= 2) View.VISIBLE else View.GONE
    } ?: run {
        view.visibility = View.GONE
    }
}

@BindingAdapter("mainOptionImage", "componentName")
fun setMainOptionImage(
    view: ImageView,
    mainOptionImages: List<Map<String, Int>>,
    componentName: String?
) {
    componentName?.let {
        for (map in mainOptionImages) {
            map[componentName]?.let { resId ->
                view.setImageResource(resId)
            }
        }
    }
}

@BindingAdapter("optionInfo")
fun setOptionInfo(view: HyundaiButtonView, optionInfo: OptionInfo?) {
    view.binding.optionInfo = optionInfo
}

@BindingAdapter("visible")
fun setOnCustomClick(view: HyundaiButtonView, isVisible: Int) {
    view.binding.isVisible = isVisible
}

@BindingAdapter("customOnClickAction")
fun setCustomOnClickAction(view: HyundaiButtonView, listener: OnHyundaiButtonClickListener?) {
    if (listener != null) {
        view.setOnClickAction { listener.onHyundaiButtonClick() }
    }
}

@BindingAdapter("customTopConstraint")
fun setCustomTopConstraint(view: View, targetId: Int) {
    val parent = view.parent as? ConstraintLayout ?: return
    val constraintSet = ConstraintSet()
    constraintSet.clone(parent)
    constraintSet.connect(view.id, ConstraintSet.TOP, targetId, ConstraintSet.BOTTOM)
    constraintSet.applyTo(parent)
}

@BindingAdapter("customLayoutHeight")
fun setCustomLayoutHeight(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("customMarginBottom")
fun setCustomMarginBottom(view: View, marginBottom: Float) {
    if (view.layoutParams is MarginLayoutParams) {
        val p = view.layoutParams as MarginLayoutParams
        p.setMargins(p.leftMargin, p.topMargin, p.rightMargin, marginBottom.toInt())
        view.requestLayout()
    }
}

@BindingAdapter(value = ["myCarData", "viewType"], requireAll = false)
fun bindRecyclerView(
    recyclerView: RecyclerView,
    myCarData: List<Map<String, List<OptionInfo>>>?,
    viewType: String
) {
    val adapter = recyclerView.adapter as? EstimateSummaryAdapter ?: EstimateSummaryAdapter()
    recyclerView.adapter = adapter

    // xml 이동
    if (recyclerView.layoutManager == null) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    val matchedOptionsMap = mutableMapOf<String, MutableList<OptionInfo>>()

    myCarData?.forEach { mapEntry ->
        mapEntry.forEach { (key, optionList) ->
            optionList.forEach { option ->
                if (option.optionType == viewType) {
                    matchedOptionsMap[key] =
                        matchedOptionsMap.getOrDefault(key, mutableListOf()).apply {
                            add(option)
                        }
                }
            }
        }
    }
    adapter.updateOptionInfo(matchedOptionsMap)
}

@BindingAdapter(value = ["currentTypeForBackground", "visibleForBackground"], requireAll = false)
fun View.setCurrentType(currentType: String?, visible: Int) {
    val backgroundView = this.findViewById<View>(R.id.v_background)
    when (visible) {
        1 -> {
            when (currentType) {
                "SelfMode" -> backgroundView.setBackgroundResource(R.drawable.style_self_mode_button)
                "GuideMode" -> backgroundView.setBackgroundResource(R.drawable.style_guide_mode_button)
            }
        }

        else -> backgroundView.setBackgroundResource(R.drawable.btn_unselect_style)
    }
}

@BindingAdapter(value = ["currentTypeForIcon", "visibleForIcon"], requireAll = false)
fun View.setCustomIcon(currentType: String?, visible: Int) {
    val icon = this.findViewById<ImageView>(R.id.iv_check)
    when (visible) {
        1 -> {
            when (currentType) {
                "SelfMode" -> icon.setImageResource(R.drawable.ic_check_on)
                "GuideMode" -> icon.setImageResource(R.drawable.ic_check_guide)
            }
        }

        else -> icon.setImageResource(R.drawable.ic_check_off)
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter(value = ["currentTypeForText", "visibleForText"], requireAll = false)
fun View.setTextColor(currentType: String?, visible: Int) {
    val rate = this.findViewById<TextView>(R.id.tv_rate)
    when (visible) {
        1 -> {
            when (currentType) {
                "SelfMode" -> rate.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.main_hyundai_blue
                    )
                )

                "GuideMode" -> rate.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.sub_active_blue
                    )
                )
            }
        }

        else -> rate.setTextColor(ContextCompat.getColor(context, R.color.cool_grey_003))
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter("visibleForTextNamePrice")
fun View.setTextColorForHyundaiButton(visible: Int) {
    val name = this.findViewById<TextView>(R.id.tv_name)
    val price = this.findViewById<TextView>(R.id.tv_price)
    when (visible) {
        1 -> {
            name.setTextColor(ContextCompat.getColor(context, R.color.main_hyundai_blue))
            price.setTextColor(ContextCompat.getColor(context, R.color.main_hyundai_blue))
        }

        else -> {
            name.setTextColor(ContextCompat.getColor(context, R.color.cool_grey_003))
            price.setTextColor(ContextCompat.getColor(context, R.color.cool_grey_003))
        }
    }
}

@BindingAdapter(value = ["animateOnClick", "currentType"], requireAll = false)
fun HyundaiButtonView.bindAnimateOnClick(shouldAnimate: Boolean, currentType: String?) {
    if (shouldAnimate && currentType == "GuideMode") {
        this.setOnClickListener {
            this.animateBorder()
        }
    } else {
        this.setOnClickListener(null)
    }
}

@BindingAdapter(value = ["currentTypeForBorder", "visibleForBorder"], requireAll = false)
fun HyundaiButtonView.borderVisible(currentType: String?, visible: Int) {
    val bottomLine = this.findViewById<View>(R.id.v_bottom_line)
    val topLine = this.findViewById<View>(R.id.v_top_line)
    val leftLine = this.findViewById<View>(R.id.v_left_line)
    val rightLine = this.findViewById<View>(R.id.v_right_line)
    if (currentType == "GuideMode") {
        if (visible == 0) {
            bottomLine.visibility = INVISIBLE
            topLine.visibility = INVISIBLE
            leftLine.visibility = INVISIBLE
            rightLine.visibility = INVISIBLE
        }
    } else {
        bottomLine.visibility = INVISIBLE
        topLine.visibility = INVISIBLE
        leftLine.visibility = INVISIBLE
        rightLine.visibility = INVISIBLE
    }
}

@BindingAdapter("app:title")
fun setTitle(view: HeaderToolBarView, title: String?) {
    view.setTitle(title ?: "")
}

@BindingAdapter(
    value = ["currentTypeForBorderAnimation", "visibleForBorderAnimation"],
    requireAll = false
)
fun HyundaiButtonView.borderAnimation(currentType: String?, visible: Int) {
    if (currentType == "GuideMode" && visible == 1) {
        this.animateBorder()
    }
}

@BindingAdapter(value = ["currentTypeForTag", "visibleForTag", "tagData"], requireAll = false)
fun setTag(textView: TextView, currentTypeForTag: String?, visible: Int, tags: List<String>?) {

    if (tags == null) return  // null 체크
    val spannable = SpannableStringBuilder()
    for (word in tags) {
        val start = spannable.length
        spannable.append(word)
        spannable.append("   ") // 각 단어 사이에 공백 추가
        spannable.setSpan(
            RoundedBackgroundSpan(textView.context),
            start,
            start + word.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    when (visible) {
        1 -> {
            textView.visibility = VISIBLE
            when (currentTypeForTag) {
                "GuideMode" -> {
                    textView.text = spannable
                }
            }
        }

        else -> {
            textView.visibility = INVISIBLE
        }
    }
}

@BindingAdapter(value = ["mainFeedbackText", "subFeedbackText"], requireAll = false)
fun setFeedbackText(view: FeedbackView, mainFeedbackText: String?, subFeedbackText: String?) {
    mainFeedbackText?.let {
        view.setMainFeedbackText(it)
    }

    subFeedbackText?.let {
        view.setSubFeedbackText(it)
    }
}

@BindingAdapter("formattedCurrency")
fun bindFormattedCurrency(view: TextView, value: Int) {
    view.text = "%,d원".format(value)
}

@BindingAdapter("plusPrice")
fun setPriceText(view: TextView, price: Int) {
    view.text ="+ %,d원".format(price)
}

@BindingAdapter(value = ["componentName", "subImage"], requireAll = false)
fun setSubImage(view: ImageView, componentName: String?, subImage: String?) {

    if (componentName == null || subImage == null) return

    val context = view.context
    val widthInPixels: Int
    val heightInPixels: Int

    if (componentName == "외장 색상") {
        widthInPixels = (60 * context.resources.displayMetrics.density).toInt()
        heightInPixels = (60 * context.resources.displayMetrics.density).toInt()

        Glide.with(context)
            .load(subImage)
            .circleCrop()
            .into(view)
    } else {
        widthInPixels = (150 * context.resources.displayMetrics.density).toInt()
        heightInPixels = (50 * context.resources.displayMetrics.density).toInt()

        Glide.with(context)
            .load(subImage)
            .into(view)
    }

    val params = view.layoutParams
    params.width = widthInPixels
    params.height = heightInPixels
    view.layoutParams = params
}