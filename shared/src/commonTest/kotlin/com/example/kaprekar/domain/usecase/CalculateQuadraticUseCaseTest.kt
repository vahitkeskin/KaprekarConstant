package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateQuadraticUseCaseTest {
    private val useCase = CalculateQuadraticUseCase()

    @Test
    fun calculate_solvesQuadraticEquationWithTwoRealRoots() {
        // x^2 - 5x + 6 = 0 => roots 3 and 2, discriminant = 25 - 24 = 1
        val result = useCase(1.0, -5.0, 6.0)
        assertEquals(1.0, result.discriminant, 0.0001)
        assertEquals(3.0, result.root1, 0.0001)
        assertEquals(2.0, result.root2, 0.0001)
    }
}
