package com.example.kaprekar.domain.usecase

import kotlin.math.E
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateEulerUseCaseTest {
    private val useCase = CalculateEulerUseCase()

    @Test
    fun calculate_approximatesEulerNumberE() {
        val result = useCase(terms = 10)
        assertEquals(E, result.actualEuler, 0.00001)
        assertEquals(E, result.taylorSeriesApproximation, 0.0001)
    }
}
