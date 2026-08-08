package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePascalUseCaseTest {
    private val useCase = CalculatePascalUseCase()

    @Test
    fun calculate_computesCombinationsAndPermutations() {
        val result = useCase(5, 2)
        assertEquals(10L, result.combination) // C(5,2) = 10
        assertEquals(20L, result.permutation) // P(5,2) = 20
        assertEquals(6, result.triangleRows.size)
    }
}
