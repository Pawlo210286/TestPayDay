package com.example.testpayday.core.library.resources

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.withTint(@ColorInt tintColor: Int): Drawable {
    DrawableCompat.setTint(DrawableCompat.wrap(mutate()), tintColor)
    return this
}
