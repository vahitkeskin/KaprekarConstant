package com.example.kaprekar.domain.usecase

data class GoldenRatioResult(
    val totalLength: Double,
    val segmentA: Double,
    val segmentB: Double,
    val goldenRatioValue: Double = 1.618033988749895,
    val ratioTotalToA: Double,
    val ratioAToB: Double,
    val goldenRectangleWidth: Double,
    val goldenRectangleHeight: Double
)

class CalculateGoldenRatioUseCase {

    companion object {
        const val PHI = 1.618033988749895
    }

    fun calculateFromTotal(total: Double): GoldenRatioResult {
        val l = total.coerceAtLeast(0.1)
        val a = l / PHI
        val b = l - a
        return GoldenRatioResult(
            totalLength = l,
            segmentA = a,
            segmentB = b,
            ratioTotalToA = l / a,
            ratioAToB = a / b,
            goldenRectangleWidth = l,
            goldenRectangleHeight = a
        )
    }

    fun calculateFromSegmentA(a: Double): GoldenRatioResult {
        val segA = a.coerceAtLeast(0.1)
        val l = segA * PHI
        val b = l - segA
        return GoldenRatioResult(
            totalLength = l,
            segmentA = segA,
            segmentB = b,
            ratioTotalToA = l / segA,
            ratioAToB = segA / b,
            goldenRectangleWidth = l,
            goldenRectangleHeight = segA
        )
    }
}
