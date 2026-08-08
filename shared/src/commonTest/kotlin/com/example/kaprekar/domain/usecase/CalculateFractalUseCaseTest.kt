package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertTrue

class CalculateFractalUseCaseTest {
    private val useCase = CalculateFractalUseCase()

    @Test
    fun calculate_generatesMandelbrotPoints() {
        val result = useCase(isMandelbrot = true, maxIterations = 30, gridSize = 10)
        assertTrue(result.points.isNotEmpty())
        assertEquals(30, result.maxIterations)
    }
}
