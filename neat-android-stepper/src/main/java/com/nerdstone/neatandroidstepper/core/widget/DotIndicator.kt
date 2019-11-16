package com.nerdstone.neatandroidstepper.core.widget

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.RestrictTo
import androidx.core.content.ContextCompat
import com.nerdstone.neatandroidstepper.core.R

@RestrictTo(RestrictTo.Scope.LIBRARY)
class DotIndicator(context: Context?) : LinearLayout(context, null) {

    var dotsCount = 1
        set(value) {
            removeAllViews()
            val view = View(context)
            view.setBackgroundResource(R.drawable.nas_dot_indicator)
            repeat((0..value).count()) {
                addView(view)
            }
            field = value
        }

    @ColorInt
    var indicatorColorSelected = ContextCompat.getColor(context!!, R.color.dotIndicatorDefault)
        set(value) {
            ContextCompat.getColor(context!!, value)
            field = value
        }

    @ColorInt
    var indicatorColorDefault = ContextCompat.getColor(context!!, R.color.dotIndicatorSelected)
        set(value) {
            ContextCompat.getColor(context!!, value)
            field = value
        }

}