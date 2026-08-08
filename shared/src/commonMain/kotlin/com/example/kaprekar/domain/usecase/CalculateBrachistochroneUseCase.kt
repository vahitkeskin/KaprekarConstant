package com.example.kaprekar.domain.usecase

import kotlin.math.PI
import kotlin.math.sqrt

data class BrachistochroneResult(
    val dropHeight: Double,
    val distance: Double,
    val cycloidTime: Double,
    val straightLineTime: Double,
    val circularArcTime: Double,
    val timeDifference: Double
)

class CalculateBrachistochroneUseCase {
    operator fun invoke(dropHeight: Double = 10.0, distance: Double = 10.0): BrachistochroneResult {
        val g = 9.81
        val h = if (dropHeight <= 0) 10.0 else dropHeight
        val d = if (distance <= 0) 10.0 else distance

        // Brachistochrone cycloid time T_cycloid = pi * sqrt(h / g) for optimal cycloid drop
        val tCycloid = PI * sqrt(h / g)

        // Straight line time T_straight = sqrt(2 * (h^2 + d^2) / (g * h))
        val tStraight = sqrt(2.0 * (h * h + d * d) / (g * h))

        // Circular arc time approximation
        val tCircle = tCycloid * 1.08

        val diff = tStraight - tCycloid

        return BrachistochroneResult(
            dropHeight = h,
            distance = d,
            cycloidTime = tCycloid,
            straightLineTime = tStraight,
            circularArcTime = tCircle,
            timeDifference = diff
        )
    }
}
