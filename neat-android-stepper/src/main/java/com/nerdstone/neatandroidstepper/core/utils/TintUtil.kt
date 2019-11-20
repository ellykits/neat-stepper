package com.nerdstone.neatandroidstepper.core.utils

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

object TintUtil {

    /**
     * Tints a drawable with the color that is provided
     * @param drawable the drawable to tint
     * @param color tint color
     * @return tinted drawable
     */
    fun tintDrawable(drawable: Drawable?, @ColorInt color: Int): Drawable? {
        drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        return drawable
    }
}