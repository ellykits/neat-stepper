package com.nerdstone.neatandroidstepper.core.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

class StepModel private constructor(val title: CharSequence?, val subTitle: CharSequence? = null) {

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
    var isNextButtonVisible: Boolean? = true
    var isPreviousButtonVisible: Boolean? = true

    data class Builder(
        var title: CharSequence? = null,
        var subTitle: CharSequence? = null,
        var nextButtonLabel: CharSequence? = null,
        var previousButtonLabel: CharSequence? = null,
        var completeStepperLabel: CharSequence? = null,
        @DrawableRes var nextButtonDrawableResId: Int? = null,
        @DrawableRes var previousButtonDrawableResId: Int? = null,
        @ColorRes var bottomNavigationColorResId: Int? = null,
        @ColorRes var toolbarColorResId: Int? = null,
        var isNextButtonVisible: Boolean? = true,
        var isPreviousButtonVisible: Boolean? = true
    ) {
        fun title(title: CharSequence) = apply { this.title = title }

        fun subTitle(subTitle: CharSequence) = apply { this.subTitle = subTitle }

        fun previousButtonLabel(previousButtonLabel: CharSequence) =
            apply { this.previousButtonLabel = previousButtonLabel }

        fun previousButtonDrawableResource(previousButtonDrawableResId: Int) =
            apply { this.previousButtonDrawableResId = previousButtonDrawableResId }

        fun showPreviousButton(isPreviousButtonVisible: Boolean) =
            apply { this.isPreviousButtonVisible = isPreviousButtonVisible }

        fun nextButtonLabel(nextButtonLabel: CharSequence) =
            apply { this.nextButtonLabel = nextButtonLabel }

        fun nextButtonDrawableResource(nextButtonDrawableResId: Int) =
            apply { this.nextButtonDrawableResId = nextButtonDrawableResId }

        fun showNextButton(isNextButtonVisible: Boolean) =
            apply { this.isNextButtonVisible = isNextButtonVisible }

        fun completeButtonLabel(completeStepperLabel: CharSequence) =
            apply { this.completeStepperLabel = completeStepperLabel }

        fun toolbarColorResource(toolbarColorResId: Int) =
            apply { this.toolbarColorResId = toolbarColorResId }

        fun bottomNavigationColorResource(bottomNavigationColorResId: Int) =
            apply { this.bottomNavigationColorResId = bottomNavigationColorResId }

        fun build(): StepModel {
            return StepModel(title, subTitle).also {
                it.previousButtonLabel = previousButtonLabel
                it.previousButtonDrawableResId = previousButtonDrawableResId
                it.isPreviousButtonVisible = isPreviousButtonVisible
                it.nextButtonLabel = nextButtonLabel
                it.nextButtonDrawableResId = nextButtonDrawableResId
                it.isNextButtonVisible = isNextButtonVisible
                it.completeStepperLabel = completeStepperLabel
                it.toolbarColorResId = toolbarColorResId
                it.bottomNavigationColorResId = bottomNavigationColorResId
            }
        }
    }
}