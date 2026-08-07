package com.example.kaprekar.domain.usecase

import kotlin.math.sqrt

data class QuadraticResult(
    val a: Double,
    val b: Double,
    val c: Double,
    val discriminant: Double,
    val vertexX: Double,
    val vertexY: Double,
    val root1Str: String,
    val root2Str: String,
    val hasRealRoots: Boolean
)

class CalculateQuadraticUseCase {
    operator fun invoke(a: Double, b: Double, c: Double): QuadraticResult {
        if (a == 0.0) {
            val root = if (b != 0.0) -c / b else 0.0
            return QuadraticResult(a, b, c, 0.0, 0.0, 0.0, root.toString(), root.toString(), true)
        }

        val delta = b * b - 4 * a * c
        val vx = -b / (2 * a)
        val vy = a * vx * vx + b * vx + c

        return if (delta >= 0) {
            val r1 = (-b + sqrt(delta)) / (2 * a)
            val r2 = (-b - sqrt(delta)) / (2 * a)
            QuadraticResult(a, b, c, delta, vx, vy, r1.toString(), r2.toString(), true)
        } else {
            val realPart = -b / (2 * a)
            val imagPart = sqrt(-delta) / (2 * a)
            val r1 = "$realPart + ${imagPart}i"
            val r2 = "$realPart - ${imagPart}i"
            QuadraticResult(a, b, c, delta, vx, vy, r1, r2, false)
        }
    }
}
