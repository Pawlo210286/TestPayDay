package com.example.testpayday.di

import com.example.testpayday.data.PayDayRemoteRepository
import com.example.testpayday.data.local.RuntimeStorage
import com.example.testpayday.domain.PayDayRepository
import com.example.testpayday.domain.RuntimeRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object DataModule {
    fun get() = Kodein.Module("Data") {
        bind<PayDayRepository>() with provider {
            PayDayRemoteRepository(
                api = instance()
            )
        }

        bind<RuntimeRepository>() with singleton {
            RuntimeStorage()
        }
    }
}
