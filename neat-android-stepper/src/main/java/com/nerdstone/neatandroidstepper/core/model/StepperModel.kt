package com.nerdstone.neatandroidstepper.core.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.nerdstone.neatandroidstepper.core.R

class StepperModel private constructor() {

    var nextButtonLabel: CharSequence? = null
    var previousButtonLabel: CharSequence? = null
    var completeStepperLabel: CharSequence? = null
    @ColorRes
    var toolbarColorResId: Int? = null
    @ColorRes
    var bottomNavigationColorResId: Int? = null
    @DrawableRes
    var nextButtonDrawableResId: Int? = null
    @DrawableRes
    var previousButtonDrawableResId: Int? = null
    @DrawableRes
    var exitButtonDrawableResId: Int? = null
    @DrawableRes
    var completeButtonDrawableResId: Int? = null
    var showBottomNavigationButtons: Boolean? = true
    var showToolbar: Boolean? = true
    var showProgressIndicator: Boolean? = true
    var indicatorType: IndicatorType? = IndicatorType.PROGRESS_BAR_INDICATOR
    var dotIndicatorColors: Pair<Int, Int>? = null

    data class Builder(
        var nextButtonLabel: CharSequence? = null,
        var previousButtonLabel: CharSequence? = null,
        var completeStepperLabel: CharSequence? = null,
        @DrawableRes var nextButtonDrawableResId: Int = R.drawable.ic_chevron_right,
        @DrawableRes var previousButtonDrawableResId: Int = R.drawable.ic_chevron_left,
        @DrawableRes var exitButtonDrawableResId: Int = R.drawable.ic_clear,
        @DrawableRes var completeButtonDrawableResId: Int = R.drawable.ic_check,
        @ColorRes var toolbarColorResId: Int = R.color.stepperToolbar,
        @ColorRes var bottomNavigationColorResId: Int? = android.R.color.transparent,
        var showBottomNavigationButtons: Boolean? = true,
        var showToolbar: Boolean? = true,
        var showProgressIndicator: Boolean? = true,
        var indicatorType: IndicatorType? = IndicatorType.PROGRESS_BAR_INDICATOR,
        var dotIndicatorColors: Pair<Int, Int>? = Pair(
            R.color.dotIndicatorDefault,
            R.color.dotIndicatorSelected
        )
    ) {
        fun previousButtonLabel(previousButtonLabel: CharSequence) =
            apply { this.previousButtonLabel = previousButtonLabel }

        fun previousButtonDrawableResource(previousButtonDrawableResId: Int) =
            apply { this.previousButtonDrawableResId = previousButtonDrawableResId }

        fun exitButtonDrawableResource(exitButtonDrawableResId: Int) =
            apply { this.exitButtonDrawableResId = exitButtonDrawableResId }

        fun completeButtonDrawableResource(completeButtonDrawableResId: Int) =
            apply { this.completeButtonDrawableResId = completeButtonDrawableResId }

        fun nextButtonLabel(nextButtonLabel: CharSequence) =
            apply { this.nextButtonLabel = nextButtonLabel }

        fun nextButtonDrawableResource(nextButtonDrawableResId: Int) =
            apply { this.nextButtonDrawableResId = nextButtonDrawableResId }

        fun completeButtonLabel(completeStepperLabel: CharSequence) =
            apply { this.completeStepperLabel = completeStepperLabel }

        fun toolbarColorResource(toolbarColorResId: Int) =
            apply { this.toolbarColorResId = toolbarColorResId }

        fun bottomNavigationColorResource(bottomNavigationColorResId: Int) =
            apply { this.bottomNavigationColorResId = bottomNavigationColorResId }

        fun showBottomNavigationButtons(showBottomNavigationButtons: Boolean) =
            apply { this.showBottomNavigationButtons = showBottomNavigationButtons }

        fun showProgressIndicator(showProgressIndicator: Boolean) =
            apply { this.showProgressIndicator = showProgressIndicator }

        fun showToolbar(showToolbar: Boolean) =
            apply { this.showToolbar = showToolbar }

        fun indicatorType(indicatorType: IndicatorType) =
            apply { this.indicatorType = indicatorType }

        fun changeDotIndicatorColors(@ColorRes defaultColorRes: Int, @ColorRes selectedColorRes: Int) =
            apply {
                this.dotIndicatorColors = Pair(defaultColorRes, selectedColorRes)
            }

        fun build(): StepperModel {
            return StepperModel().also {
                it.previousButtonLabel = previousButtonLabel
                it.previousButtonDrawableResId = previousButtonDrawableResId
                it.nextButtonLabel = nextButtonLabel
                it.nextButtonDrawableResId = nextButtonDrawableResId
                it.completeStepperLabel = completeStepperLabel
                it.toolbarColorResId = toolbarColorResId
                it.bottomNavigationColorResId = bottomNavigationColorResId
                it.showBottomNavigationButtons = showBottomNavigationButtons
                it.showProgressIndicator = showProgressIndicator
                it.showToolbar = showToolbar
                it.exitButtonDrawableResId = exitButtonDrawableResId
                it.completeButtonDrawableResId = completeButtonDrawableResId
                it.indicatorType = indicatorType
                it.dotIndicatorColors = dotIndicatorColors
            }
        }
    }

    enum class IndicatorType {
        DOT_INDICATOR,
        PROGRESS_BAR_INDICATOR
    }
}