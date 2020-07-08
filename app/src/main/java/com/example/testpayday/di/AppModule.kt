package com.example.testpayday.di

import android.app.Application
import com.example.testpayday.presentation.login.LoginRouter
import com.example.testpayday.presentation.main.MainRouter
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object AppModule {

    fun get(application: Application) = Kodein.Module("App") {
        import(androidModule(application))
        import(NetworkModule.get())
        import(DataModule.get())
        import(DomainModule.get())

        bind() from singleton {
            MainRouter()
        }

        bind() from singleton {
            LoginRouter()
        }
    }
}
