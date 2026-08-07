package com.example.kaprekar.di

import com.example.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.example.kaprekar.presentation.KaprekarViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factoryOf(::CalculateKaprekarUseCase)
    viewModelOf(::KaprekarViewModel)
}
