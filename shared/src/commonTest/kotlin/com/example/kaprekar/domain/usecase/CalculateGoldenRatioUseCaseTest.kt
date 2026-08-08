package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculateGoldenRatioUseCaseTest {
    private val useCase = CalculateGoldenRatioUseCase()

    @Test
    fun calculate_dividesLineInGoldenRatio() {
        val result = useCase(100.0)
        assertEquals(1.618033988749895, result.phi, 0.0001)
        val ratio = result.longerSegment / result.shorterSegment
        assertEquals(result.phi, ratio, 0.0001)
    }
}
