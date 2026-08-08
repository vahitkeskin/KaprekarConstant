package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateEuclidGcdUseCaseTest {
    private val useCase = CalculateEuclidGcdUseCase()

    @Test
    fun calculate_computesGcdAndLcmFor48And18() {
        val result = useCase(48L, 18L)
        assertEquals(6L, result.gcd)
        assertEquals(144L, result.lcm)
        assertEquals(3, result.steps.size)
    }
}
