package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class CalculateSuperNumberUseCaseTest {
    private val useCase = CalculateSuperNumberUseCase()

    @Test
    fun calculate_identifiesArmstrongNumber153() {
        val result = useCase(153)
        assertTrue(result.isArmstrong, "Expected 153 to be an Armstrong number")
    }

    @Test
    fun calculate_identifiesHarshadNumber18() {
        val result = useCase(18)
        assertTrue(result.isHarshad, "Expected 18 to be a Harshad number (18 / (1+8) = 2)")
    }

    @Test
    fun calculate_identifiesPerfectNumber28() {
        val result = useCase(28)
        assertTrue(result.isPerfect, "Expected 28 to be a Perfect number (1+2+4+7+14 = 28)")
    }

    @Test
    fun calculate_identifiesAutomorphicNumber25() {
        val result = useCase(25)
        assertTrue(result.isAutomorphic, "Expected 25 to be Automorphic (25^2 = 625)")
    }
}
