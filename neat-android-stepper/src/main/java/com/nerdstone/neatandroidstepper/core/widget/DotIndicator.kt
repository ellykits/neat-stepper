package com.nerdstone.neatandroidstepper.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.nerdstone.neatandroidstepper.core.R
import com.nerdstone.neatandroidstepper.core.utils.TintUtil

class DotIndicator : LinearLayout {

    private val fullScale = 1f
    private val halfScale = 0.5f
    private val durationImmediate = 0
    private val scaleAnimationDefaultDuration = 500
    private val decelerateInterpolator = DecelerateInterpolator()

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView()
    }

    var dotCount = 1
    private var currentDot: Int = 0

    fun addDots(dotCount: Int) {
        this.dotCount = dotCount
        removeAllViews()
        repeat((0 until this.dotCount).count()) {
            val view = LayoutInflater.from(context).inflate(R.layout.nas_dot, this, false)
            addView(view)
        }
    }

    @ColorInt
    var indicatorColorSelected = ContextCompat.getColor(context!!, R.color.dotIndicatorSelected)

    @ColorInt
    var indicatorColorDefault = ContextCompat.getColor(context!!, R.color.dotIndicatorDefault)


    private fun initView() {
        orientation = HORIZONTAL
        isHorizontalScrollBarEnabled = true
        gravity = Gravity.CENTER_HORIZONTAL
        addDots(1)
    }

    /**
     * Changes the currently selected dot and updates the UI accordingly
     * @param current the new currently selected dot
     * @param shouldAnimate true if the change should be animated, false otherwise
     */
    fun setCurrentDot(current: Int, shouldAnimate: Boolean) {
        this.currentDot = current
        applyAnimation(shouldAnimate)
    }

    private fun applyAnimation(shouldAnimate: Boolean) {
        (0 until dotCount).forEach { index ->
            if (index == currentDot) {
                getChildAt(index).animate()
                    .scaleX(fullScale)
                    .scaleY(fullScale)
                    .setDuration((if (shouldAnimate) scaleAnimationDefaultDuration else durationImmediate).toLong())
                    .setInterpolator(decelerateInterpolator)
                    .start()
                colorChildAtPosition(index, true)
            } else {
                getChildAt(index).animate()
                    .scaleX(halfScale)
                    .scaleY(halfScale)
                    .setDuration((if (shouldAnimate) scaleAnimationDefaultDuration else durationImmediate).toLong())
                    .setInterpolator(decelerateInterpolator)
                    .start()
                colorChildAtPosition(index, false)
            }
        }
    }

    private fun colorChildAtPosition(childPosition: Int, selected: Boolean) {
        TintUtil.tintDrawable(
            getChildAt(childPosition).background,
            if (selected) indicatorColorSelected else indicatorColorDefault
        )
    }
}