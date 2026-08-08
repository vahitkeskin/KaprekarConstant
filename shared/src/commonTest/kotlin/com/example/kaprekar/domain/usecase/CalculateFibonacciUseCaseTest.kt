package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculateFibonacciUseCaseTest {
    private val useCase = CalculateFibonacciUseCase()

    @Test
    fun calculate_returnsFirst10TermsCorrectly() {
        val result = useCase(10)
        assertEquals(10, result.sequence.size)
        assertEquals(0L, result.sequence[0])
        assertEquals(1L, result.sequence[1])
        assertEquals(1L, result.sequence[2])
        assertEquals(2L, result.sequence[3])
        assertEquals(3L, result.sequence[4])
        assertEquals(5L, result.sequence[5])
        assertEquals(8L, result.sequence[6])
        assertEquals(13L, result.sequence[7])
        assertEquals(21L, result.sequence[8])
        assertEquals(34L, result.sequence[9])
    }

    @Test
    fun calculate_goldenRatioApproaches1_618() {
        val result = useCase(20)
        val ratio = result.goldenRatioApproximation
        assertTrue(ratio > 1.617 && ratio < 1.619, "Expected Golden Ratio approximation to be ~1.618, got $ratio")
    }
}
