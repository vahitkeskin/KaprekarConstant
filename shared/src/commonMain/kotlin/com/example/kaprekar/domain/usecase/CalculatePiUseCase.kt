package com.example.kaprekar.domain.usecase

import kotlin.math.sqrt
import kotlin.random.Random

data class MonteCarloPoint(val x: Double, val y: Double, val isInside: Boolean)

data class PiResult(val estimatedPi: Double, val totalPoints: Int, val insidePoints: Int, val samplePoints: List<MonteCarloPoint>, val nilakanthaSeries: List<Double>)

class CalculatePiUseCase {
    operator fun invoke(pointCount: Int = 1000, seriesTerms: Int = 10): PiResult {
        var inside = 0
        val samplePoints = mutableListOf<MonteCarloPoint>()

        for (i in 0 until pointCount) {
            val x = Random.nextDouble(-1.0, 1.0)
            val y = Random.nextDouble(-1.0, 1.0)
            val isInside = (x * x + y * y) <= 1.0
            if (isInside) inside++

            if (i < 80) {
                samplePoints.add(MonteCarloPoint(x, y, isInside))
            }
        }

        val estimatedPi = 4.0 * inside / pointCount.toDouble()

        // Nilakantha Series: 3 + 4/(2*3*4) - 4/(4*5*6) + 4/(6*7*8) - ...
        val series = mutableListOf<Double>()
        var currentPi = 3.0
        series.add(currentPi)
        var sign = 1.0

        for (n in 1..seriesTerms) {
            val k = 2.0 * n
            val term = sign * (4.0 / (k * (k + 1.0) * (k + 2.0)))
            currentPi += term
            series.add(currentPi)
            sign = -sign
        }

        return PiResult(estimatedPi, pointCount, inside, samplePoints, series)
    }
}
