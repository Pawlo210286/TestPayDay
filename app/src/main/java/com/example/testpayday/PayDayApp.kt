package com.example.testpayday

import android.app.Application
import com.example.testpayday.di.AppModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import timber.log.Timber

class PayDayApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(AppModule.get(this@PayDayApp))
    }

    override val kodeinTrigger = KodeinTrigger()
    override fun onCreate() {
        super.onCreate()

        kodeinTrigger.trigger()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}
