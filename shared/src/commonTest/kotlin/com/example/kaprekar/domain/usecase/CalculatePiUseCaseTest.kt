package com.example.kaprekar.domain.usecase

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePiUseCaseTest {
    private val useCase = CalculatePiUseCase()

    @Test
    fun calculate_approximatesPiUsingMonteCarloAndNilakantha() {
        val result = useCase(sampleCount = 1000)
        assertEquals(PI, result.actualPi, 0.00001)
        assertEquals(PI, result.nilakanthaPi, 0.05)
    }
}
