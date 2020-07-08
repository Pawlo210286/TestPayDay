package com.example.testpayday.di

import android.content.Context
import com.example.testpayday.BuildConfig
import com.example.testpayday.data.network.api.PayDayApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    fun get() = Kodein.Module("Network") {
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(instance())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val builder = OkHttpClient.Builder()

            builder.cache(instance())

            builder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            builder.build()
        }

        bind() from provider {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            Cache(instance<Context>().cacheDir, cacheSize.toLong())
        }

        bind<PayDayApi>() with singleton {
            instance<Retrofit>().create(PayDayApi::class.java)
        }
    }

    private const val TIMEOUT_SEC: Long = 5
}
