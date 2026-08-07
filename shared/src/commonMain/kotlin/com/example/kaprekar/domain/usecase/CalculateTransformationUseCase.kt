package com.example.kaprekar.domain.usecase

data class TransformedVector(val origX: Double, val origY: Double, val newX: Double, val newY: Double)

data class TransformationResult(
    val m00: Double, val m01: Double,
    val m10: Double, val m11: Double,
    val determinant: Double,
    val gridVectors: List<TransformedVector>
)

class CalculateTransformationUseCase {
    operator fun invoke(m00: Double, m01: Double, m10: Double, m11: Double): TransformationResult {
        val det = m00 * m11 - m01 * m10
        val vectors = mutableListOf<TransformedVector>()

        for (x in -5..5) {
            for (y in -5..5) {
                val nx = m00 * x + m01 * y
                val ny = m10 * x + m11 * y
                vectors.add(TransformedVector(x.toDouble(), y.toDouble(), nx, ny))
            }
        }

        return TransformationResult(m00, m01, m10, m11, det, vectors)
    }
}
