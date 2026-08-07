package com.example.kaprekar.di

import com.example.kaprekar.data.repository.ThemeRepositoryImpl
import com.example.kaprekar.domain.repository.ThemeRepository
import com.example.kaprekar.domain.usecase.CalculateChaosGameUseCase
import com.example.kaprekar.domain.usecase.CalculateCollatzUseCase
import com.example.kaprekar.domain.usecase.CalculateEuclidGcdUseCase
import com.example.kaprekar.domain.usecase.CalculateEulerUseCase
import com.example.kaprekar.domain.usecase.CalculateFibonacciUseCase
import com.example.kaprekar.domain.usecase.CalculateFourierUseCase
import com.example.kaprekar.domain.usecase.CalculateFractalUseCase
import com.example.kaprekar.domain.usecase.CalculateGoldenRatioUseCase
import com.example.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.example.kaprekar.domain.usecase.CalculateModularUseCase
import com.example.kaprekar.domain.usecase.CalculateNimGameUseCase
import com.example.kaprekar.domain.usecase.CalculatePascalUseCase
import com.example.kaprekar.domain.usecase.CalculatePhyllotaxisUseCase
import com.example.kaprekar.domain.usecase.CalculatePiUseCase
import com.example.kaprekar.domain.usecase.CalculatePrimeUseCase
import com.example.kaprekar.domain.usecase.CalculateQuadraticUseCase
import com.example.kaprekar.domain.usecase.CalculateStatisticsUseCase
import com.example.kaprekar.domain.usecase.CalculateSuperNumberUseCase
import com.example.kaprekar.domain.usecase.CalculateTransformationUseCase
import com.example.kaprekar.domain.usecase.CalculateTrigonometryUseCase
import com.example.kaprekar.presentation.KaprekarViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    includes(platformModule())
    factoryOf(::CalculateKaprekarUseCase)
    factoryOf(::CalculateFibonacciUseCase)
    factoryOf(::CalculateSuperNumberUseCase)
    factoryOf(::CalculateGoldenRatioUseCase)
    factoryOf(::CalculateCollatzUseCase)
    factoryOf(::CalculatePrimeUseCase)
    factoryOf(::CalculatePascalUseCase)
    factoryOf(::CalculatePiUseCase)
    factoryOf(::CalculateEulerUseCase)
    factoryOf(::CalculateEuclidGcdUseCase)
    factoryOf(::CalculateTrigonometryUseCase)
    factoryOf(::CalculateQuadraticUseCase)
    factoryOf(::CalculateModularUseCase)
    factoryOf(::CalculateStatisticsUseCase)
    factoryOf(::CalculateFractalUseCase)
    factoryOf(::CalculatePhyllotaxisUseCase)
    factoryOf(::CalculateTransformationUseCase)
    factoryOf(::CalculateFourierUseCase)
    factoryOf(::CalculateChaosGameUseCase)
    factoryOf(::CalculateNimGameUseCase)

    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    viewModelOf(::KaprekarViewModel)
}
