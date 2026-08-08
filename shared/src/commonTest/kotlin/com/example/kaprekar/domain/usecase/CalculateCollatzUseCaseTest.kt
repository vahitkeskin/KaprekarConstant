package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculateCollatzUseCaseTest {
    private val useCase = CalculateCollatzUseCase()

    @Test
    fun calculate_collatzConjectureForNumber6() {
        val result = useCase(6L)
        // 6 -> 3 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
        assertEquals(1L, result.sequence.last())
        assertEquals(52L, result.peakValue)
        assertTrue(result.stepCount > 0)
    }
}
