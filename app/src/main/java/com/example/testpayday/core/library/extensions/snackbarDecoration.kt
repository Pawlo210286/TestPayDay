package com.example.testpayday.core.library.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import com.example.testpayday.R
import com.example.testpayday.core.library.resources.getColor
import com.google.android.material.snackbar.Snackbar

fun Snackbar.decorate(@ColorRes backgroundId: Int, @ColorRes textColorId: Int): Snackbar {
    val layout = view as Snackbar.SnackbarLayout

    val textView = with(layout) {
        setBackgroundColor(getColor(backgroundId))
        findViewById<TextView>(R.id.snackbar_text)
    }

    with(textView) {
        setTextColor(getColor(textColorId))
        maxLines = Int.MAX_VALUE
        ellipsize = null
    }

    with(layout.findViewById(R.id.snackbar_action) as TextView) {
        setTextColor(getColor(textColorId))
    }

    return this
}
