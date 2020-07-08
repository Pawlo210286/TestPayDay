package com.example.testpayday.di

import com.example.testpayday.domain.interactor.AccountInteractor
import com.example.testpayday.domain.interactor.AuthInteractor
import com.example.testpayday.domain.interactor.DashboardInteractor
import com.example.testpayday.domain.interactor.TransactionsInteractor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object DomainModule {
    fun get() = Kodein.Module("Domain") {
        bind() from provider {
            TransactionsInteractor(
                remote = instance(),
                runtime = instance()
            )
        }

        bind() from provider {
            DashboardInteractor(
                remote = instance(),
                runtime = instance()
            )
        }

        bind() from provider {
            AuthInteractor(
                remote = instance(),
                runtime = instance()
            )
        }

        bind() from provider {
            AccountInteractor(
                remote = instance(),
                runtime = instance()
            )
        }
    }
}
