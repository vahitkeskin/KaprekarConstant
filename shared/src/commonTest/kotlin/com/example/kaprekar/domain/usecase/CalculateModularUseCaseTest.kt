package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateModularUseCaseTest {
    private val useCase = CalculateModularUseCase()

    @Test
    fun calculate_modularExponentiationAndTotient() {
        // 2^10 mod 1000 = 1024 mod 1000 = 24
        val result = useCase(2L, 10L, 1000L)
        assertEquals(24L, result.modularResult)
        assertEquals(400L, result.totientValue) // phi(1000) = 400
    }
}
