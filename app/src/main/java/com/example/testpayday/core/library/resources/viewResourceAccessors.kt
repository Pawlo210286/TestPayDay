package com.example.testpayday.core.library.resources

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun View.getString(@StringRes resId: Int): String = context.getString(resId)

@Suppress("SpreadOperator")
fun View.getString(@StringRes resId: Int, vararg formatArgs: Any): String = context.getString(resId, *formatArgs)

fun View.getDrawable(@DrawableRes drawableRes: Int): Drawable? = AppCompatResources.getDrawable(context, drawableRes)

fun View.requireDrawable(@DrawableRes drawableRes: Int): Drawable = checkNotNull(getDrawable(drawableRes))

fun View.getDimension(@DimenRes id: Int): Float = resources.getDimension(id)

@ColorInt
fun View.getColor(@ColorRes colorResId: Int): Int = ContextCompat.getColor(context, colorResId)

fun View.getDrawableWithTintRes(@DrawableRes drawableRes: Int, @ColorRes tintColorRes: Int): Drawable? {
    return if (tintColorRes != 0) {
        getDrawableWithTint(drawableRes, getColor(tintColorRes))
    } else {
        getDrawable(drawableRes)
    }
}

fun View.getDrawableWithTint(@DrawableRes drawableRes: Int, @ColorInt tintColor: Int): Drawable? {
    return getDrawable(drawableRes)?.withTint(tintColor)
}

