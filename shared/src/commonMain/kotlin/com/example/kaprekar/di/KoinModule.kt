package com.example.kaprekar.di

import com.example.kaprekar.data.repository.ThemeRepositoryImpl
import com.example.kaprekar.domain.repository.ThemeRepository
import com.example.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.example.kaprekar.presentation.KaprekarViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    includes(platformModule())
    factoryOf(::CalculateKaprekarUseCase)
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    viewModelOf(::KaprekarViewModel)
}
