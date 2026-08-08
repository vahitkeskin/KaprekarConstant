package com.example.kaprekar.domain.usecase

import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTrigonometryUseCaseTest {
    private val useCase = CalculateTrigonometryUseCase()

    @Test
    fun calculate_computesTrigValuesFor30Degrees() {
        val result = useCase(30.0)
        assertEquals(0.5, result.sinValue, 0.0001)
        assertEquals(sqrt(3.0) / 2.0, result.cosValue, 0.0001)
    }
}
