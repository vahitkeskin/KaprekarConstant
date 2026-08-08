package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class CalculatePrimeUseCaseTest {
    private val useCase = CalculatePrimeUseCase()

    @Test
    fun calculate_identifiesPrime29AndComposite28() {
        val resultPrime = useCase(29L)
        assertTrue(resultPrime.isPrime, "Expected 29 to be prime")

        val resultComposite = useCase(28L)
        assertFalse(resultComposite.isPrime, "Expected 28 to be composite")
        assertEquals(listOf(2L, 2L, 7L), resultComposite.primeFactors)
    }
}
