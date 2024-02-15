package com.example.temperature.di.modules

import com.example.temperature.viewModel.MainViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelsModule = module {

    viewModel{
        MainViewModel(repository = get())
    }
}