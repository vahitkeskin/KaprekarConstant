package com.vahitkeskin.kaprekar.di

import com.vahitkeskin.kaprekar.data.repository.ThemeRepositoryImpl
import com.vahitkeskin.kaprekar.domain.repository.ThemeRepository
import com.vahitkeskin.kaprekar.domain.usecase.CalculateChaosGameUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateCollatzUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateEuclidGcdUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateEulerUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateFibonacciUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateFourierUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateFractalUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateGoldenRatioUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateModularUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateNimGameUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculatePascalUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculatePhyllotaxisUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculatePiUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculatePrimeUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateQuadraticUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateStatisticsUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateSuperNumberUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateTransformationUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateTrigonometryUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateLogarithmUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateArfInvariantUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateThalesUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateKeplerUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateBrachistochroneUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateCantorUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateEratosthenesUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateCubicUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateSphericalTrigUseCase
import com.vahitkeskin.kaprekar.domain.usecase.CalculateGodelUseCase
import com.vahitkeskin.kaprekar.presentation.KaprekarViewModel
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
    factoryOf(::CalculateLogarithmUseCase)
    factoryOf(::CalculateArfInvariantUseCase)
    factoryOf(::CalculateThalesUseCase)
    factoryOf(::CalculateKeplerUseCase)
    factoryOf(::CalculateBrachistochroneUseCase)
    factoryOf(::CalculateCantorUseCase)
    factoryOf(::CalculateEratosthenesUseCase)
    factoryOf(::CalculateCubicUseCase)
    factoryOf(::CalculateSphericalTrigUseCase)
    factoryOf(::CalculateGodelUseCase)

    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    viewModelOf(::KaprekarViewModel)
}
