package com.example.kaprekar.domain.usecase

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PhyllotaxisSeed(val index: Int, val x: Double, val y: Double, val angleRad: Double, val radius: Double)

data class PhyllotaxisResult(val seeds: List<PhyllotaxisSeed>, val goldenAngleDegrees: Double)

class CalculatePhyllotaxisUseCase {
    operator fun invoke(seedCount: Int = 300, cScale: Double = 6.0): PhyllotaxisResult {
        val goldenAngleDeg = 137.50776405003785
        val goldenAngleRad = goldenAngleDeg * kotlin.math.PI / 180.0
        val seeds = mutableListOf<PhyllotaxisSeed>()

        for (n in 0 until seedCount) {
            val a = n * goldenAngleRad
            val r = cScale * sqrt(n.toDouble())
            val x = r * cos(a)
            val y = r * sin(a)
            seeds.add(PhyllotaxisSeed(n, x, y, a, r))
        }

        return PhyllotaxisResult(seeds, goldenAngleDeg)
    }
}
