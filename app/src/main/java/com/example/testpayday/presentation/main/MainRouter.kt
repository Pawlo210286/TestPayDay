package com.example.testpayday.presentation.main

import android.content.Intent
import androidx.navigation.NavController
import com.example.testpayday.presentation.login.LoginActivity

class MainRouter {

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

    fun navigateToLogin(activity: MainActivity) {
        Intent(activity, LoginActivity::class.java).let {
            activity.startActivity(it)
        }
        activity.finish()
    }
}
