package com.example.temperature.di.modules


import com.example.temperature.repositores.MainRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        MainRepository(get())
    }

}