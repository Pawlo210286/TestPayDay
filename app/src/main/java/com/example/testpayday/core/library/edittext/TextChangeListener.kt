package com.example.testpayday.core.library.edittext

import android.text.Editable
import android.text.TextWatcher

class TextChangeListener(
    private val mOnTextChanged: (String) -> Unit
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mOnTextChanged(s.toString())
    }
}
