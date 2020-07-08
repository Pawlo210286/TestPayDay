package com.example.testpayday.presentation.login

import androidx.navigation.NavController
import com.example.testpayday.R

class LoginRouter {

    private var _controller: NavController? = null
    private val controller: NavController
        get() = _controller!!

    private var close: (() -> Unit)? = null

    fun attach(it: NavController, close: () -> Unit) {
        _controller = it
        this.close = close
    }

    fun detach() {
        _controller = null
        close = null
    }

    fun navigateToMain() {
        controller.navigate(R.id.mainActivity)
        close?.invoke()
    }

    fun navigateToSignUp() {
        controller.navigate(R.id.action_signInFragment_to_signUpFragment)
    }
}
