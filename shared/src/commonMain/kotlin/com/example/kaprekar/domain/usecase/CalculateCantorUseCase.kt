package com.example.kaprekar.domain.usecase

import kotlin.math.pow
import kotlin.math.ln

data class CantorIterationStep(
    val step: Int,
    val segmentCount: Long,
    val segmentLength: Double,
    val totalRemainingLength: Double
)

data class CantorResult(
    val maxStep: Int,
    val hausdorffDimension: Double,
    val steps: List<CantorIterationStep>
)

class CalculateCantorUseCase {
    operator fun invoke(maxStep: Int = 5): CantorResult {
        val safeStep = maxStep.coerceIn(0, 7)
        val hausdorff = ln(2.0) / ln(3.0) // ~0.63092975

        val list = mutableListOf<CantorIterationStep>()
        for (n in 0..safeStep) {
            val count = 2.0.pow(n).toLong()
            val segLen = (1.0 / 3.0).pow(n)
            val totalLen = (2.0 / 3.0).pow(n)
            list.add(CantorIterationStep(n, count, segLen, totalLen))
        }

        return CantorResult(safeStep, hausdorff, list)
    }
}
