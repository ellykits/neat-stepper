package com.nerdstone.neatandroidstepper.core.utils

import android.content.Context
import android.os.Build
import android.util.TypedValue

object ThemeColor {
    const val COLOR_ACCENT = "colorAccent"
    const val COLOR_PRIMARY = "colorPrimary"
    const val COLOR_PRIMARY_DARK = "colorPrimaryDark"
}

object ColorUtil {
    fun getThemeColor(context: Context, themeColor: String): Int {
        val colorAttr: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            when (themeColor) {
                ThemeColor.COLOR_ACCENT -> android.R.attr.colorAccent
                ThemeColor.COLOR_PRIMARY -> android.R.attr.colorPrimary
                ThemeColor.COLOR_PRIMARY_DARK -> android.R.attr.colorPrimaryDark
                else -> android.R.attr.colorAccent
            }
        } else {
            context.resources.getIdentifier(themeColor, "attr", context.packageName)
        }
        val outValue = TypedValue()
        context.theme.resolveAttribute(colorAttr, outValue, true)
        return outValue.data
    }
}