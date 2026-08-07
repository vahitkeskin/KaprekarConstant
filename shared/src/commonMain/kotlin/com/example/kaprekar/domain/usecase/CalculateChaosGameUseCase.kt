package com.example.kaprekar.domain.usecase

import kotlin.random.Random

data class ChaosPoint(val x: Double, val y: Double)

data class ChaosGameResult(val vertexA: ChaosPoint, val vertexB: ChaosPoint, val vertexC: ChaosPoint, val points: List<ChaosPoint>)

class CalculateChaosGameUseCase {
    operator fun invoke(totalPoints: Int = 1500): ChaosGameResult {
        val vA = ChaosPoint(0.0, 1.0)
        val vB = ChaosPoint(-0.866, -0.5)
        val vC = ChaosPoint(0.866, -0.5)

        val vertices = listOf(vA, vB, vC)
        val points = mutableListOf<ChaosPoint>()

        var currX = 0.0
        var currY = 0.0

        for (i in 0 until totalPoints) {
            val target = vertices[Random.nextInt(3)]
            currX = (currX + target.x) / 2.0
            currY = (currY + target.y) / 2.0

            if (i > 10) {
                points.add(ChaosPoint(currX, currY))
            }
        }

        return ChaosGameResult(vA, vB, vC, points)
    }
}
