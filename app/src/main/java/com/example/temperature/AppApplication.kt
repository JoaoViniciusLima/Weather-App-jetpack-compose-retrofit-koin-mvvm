package com.example.temperature

import android.app.Application
import com.example.temperature.di.modules.apiModule
import com.example.temperature.di.modules.repositoryModule
import com.example.temperature.di.modules.viewModelsModule
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                apiModule,
                repositoryModule,
                viewModelsModule
            )
        }
    }
}