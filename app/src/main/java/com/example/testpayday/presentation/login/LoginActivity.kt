package com.example.testpayday.presentation.login

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity(R.layout.activity_login) {

    override fun diModule() = Kodein.Module(LoginActivity::class.java.simpleName) {

    }

    private val router: LoginRouter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Navigation.findNavController(this, R.id.nav_host_fragment).let {
            it to AppBarConfiguration(it.graph)
        }.let {
            toolbar.setupWithNavController(it.first, it.second)
        }
    }

    override fun onStart() {
        super.onStart()
        Navigation.findNavController(this, R.id.nav_host_fragment).let {
            router.attach(it) {
                finish()
            }
        }
    }

    override fun onStop() {
        router.detach()
        super.onStop()
    }
}
