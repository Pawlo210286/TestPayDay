package com.example.testpayday.core.base.presentation.viewmodel

import androidx.annotation.StringRes
import com.example.testpayday.core.library.resources.StringResource

interface Event

@Suppress("DataClassPrivateConstructor")
data class ErrorMessageEvent private constructor(val errorMessageResource: StringResource) : Event {
    constructor(errorMessage: String) : this(StringResource(errorMessage))
    constructor(@StringRes errorMessage: Int) : this(StringResource(errorMessage))
}

@Suppress("DataClassPrivateConstructor")
data class MessageEvent private constructor(val messageResource: StringResource) : Event {
    constructor(message: String) : this(StringResource(message))
    constructor(@StringRes message: Int) : this(StringResource(message))
}
