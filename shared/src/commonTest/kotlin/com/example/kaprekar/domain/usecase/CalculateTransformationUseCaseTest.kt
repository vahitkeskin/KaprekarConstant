package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTransformationUseCaseTest {
    private val useCase = CalculateTransformationUseCase()

    @Test
    fun calculate_appliesMatrixTransformationToVectors() {
        // Rotation by 90 degrees: [0 -1; 1 0]
        val result = useCase(0.0, -1.0, 1.0, 0.0)
        assertEquals(-1.0, result.transformedUnitX.x, 0.0001)
        assertEquals(0.0, result.transformedUnitX.y, 0.0001)
        assertEquals(0.0, result.transformedUnitY.x, 0.0001)
        assertEquals(1.0, result.transformedUnitY.y, 0.0001)
        assertEquals(1.0, result.determinant, 0.0001)
    }
}
