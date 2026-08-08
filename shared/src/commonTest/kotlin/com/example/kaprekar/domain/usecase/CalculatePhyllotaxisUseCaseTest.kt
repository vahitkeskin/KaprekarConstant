package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePhyllotaxisUseCaseTest {
    private val useCase = CalculatePhyllotaxisUseCase()

    @Test
    fun calculate_generatesSunflowerSeedCoords() {
        val result = useCase(seedCount = 100)
        assertEquals(100, result.seeds.size)
        assertEquals(137.50776405003785, result.goldenAngleDegrees, 0.0001)
    }
}
