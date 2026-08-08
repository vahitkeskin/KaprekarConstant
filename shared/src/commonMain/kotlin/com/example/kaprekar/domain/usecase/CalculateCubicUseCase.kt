package com.example.kaprekar.domain.usecase

import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

data class CubicResult(
    val a: Double, val b: Double, val c: Double, val d: Double,
    val p: Double, val q: Double, val discriminant: Double,
    val roots: List<String>
)

class CalculateCubicUseCase {
    operator fun invoke(a: Double = 1.0, b: Double = -6.0, c: Double = 11.0, d: Double = -6.0): CubicResult {
        val safeA = if (a == 0.0) 1.0 else a

        // Depressed cubic x^3 + p*x + q = 0 by x = t - b/(3a)
        val p = (3.0 * safeA * c - b * b) / (3.0 * safeA * safeA)
        val q = (2.0 * b.pow(3.0) - 9.0 * safeA * b * c + 27.0 * safeA * safeA * d) / (27.0 * safeA.pow(3.0))

        val disc = (q / 2.0).pow(2.0) + (p / 3.0).pow(3.0)

        val roots = mutableListOf<String>()
        val shift = b / (3.0 * safeA)

        if (disc < 0) {
            // 3 distinct real roots (Casus Irreducibilis)
            val r = sqrt(- (p.pow(3.0)) / 27.0)
            val phi = acos((-q / 2.0) / r)
            val t1 = 2.0 * sqrt(-p / 3.0) * cos(phi / 3.0)
            val t2 = 2.0 * sqrt(-p / 3.0) * cos((phi + 2.0 * PI) / 3.0)
            val t3 = 2.0 * sqrt(-p / 3.0) * cos((phi + 4.0 * PI) / 3.0)

            roots.add("x1 = ${round(t1 - shift)}")
            roots.add("x2 = ${round(t2 - shift)}")
            roots.add("x3 = ${round(t3 - shift)}")
        } else {
            // 1 real root and 2 complex roots (or repeated real roots)
            val u = cbrt(-q / 2.0 + sqrt(disc))
            val v = cbrt(-q / 2.0 - sqrt(disc))
            val x1 = u + v - shift
            roots.add("x1 = ${round(x1)}")
            roots.add("x2 = ${round(-(u + v) / 2.0 - shift)} + ${round(sqrt(3.0) / 2.0 * (u - v))} i")
            roots.add("x3 = ${round(-(u + v) / 2.0 - shift)} - ${round(sqrt(3.0) / 2.0 * (u - v))} i")
        }

        return CubicResult(safeA, b, c, d, p, q, disc, roots)
    }

    private fun cbrt(x: Double): Double = if (x >= 0) x.pow(1.0 / 3.0) else -(-x).pow(1.0 / 3.0)
    private fun round(x: Double): Double = (kotlin.math.round(x * 1000.0)) / 1000.0
}
