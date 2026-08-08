package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateStatisticsUseCaseTest {
    private val useCase = CalculateStatisticsUseCase()

    @Test
    fun calculate_computesMeanMedianModeVariance() {
        val numbers = listOf(1.0, 2.0, 2.0, 3.0, 4.0, 6.0)
        val result = useCase(numbers)
        assertEquals(3.0, result.mean, 0.0001)
        assertEquals(2.5, result.median, 0.0001)
        assertEquals(2.0, result.mode, 0.0001)
    }
}
